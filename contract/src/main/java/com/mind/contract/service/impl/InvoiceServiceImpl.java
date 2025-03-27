package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.ContractDao;
import com.mind.contract.dao.InvoiceDao;
import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.dto.InvoiceDto;
import com.mind.contract.entity.dto.StatementDto;
import com.mind.contract.entity.pojo.Invoice;
import com.mind.contract.entity.pojo.TimeSum;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.InvoiceService;
import com.mind.contract.utils.EasyExcelConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName：InvoiceServiceImpl
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
/* 业务接口实现*/
@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    @Resource
    private InvoiceDao invoiceDao;
    @Resource
    private ContractDao contractDao;
    @Autowired
    private EasyExcelConfig<InvoiceDto> easyExcelConfig;

    @Override
    public void exportInvoice(HttpServletResponse httpServletResponse) {
        try {
            List<InvoiceDto> invoiceDtos = invoiceExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "进项发票明细", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), InvoiceDto.class)
                    .sheet("进项发票明细").doWrite(invoiceDtos);
        } catch (Exception e) {
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    private List<InvoiceDto> invoiceExportList() {
        List<Invoice> list = invoiceDao.selectAllInvoice();
        List<InvoiceDto> resultList = new ArrayList<>();
        for (Invoice invoice : list){
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(invoice,invoiceDto);
            resultList.add(invoiceDto);
        }
        return resultList;
    }

    @Override
    public List<TimeSum> timeSum(TimeSum timeSum) {
        List<Map<String, Object>> map = invoiceDao.timeSum(timeSum.getYear(),timeSum.getMonth());
        List<TimeSum> list=new ArrayList<>();
        map.stream().forEach(t->{
            TimeSum sum = new TimeSum();
            sum.setInvoiceType(String.valueOf(t.get("invoiceType")));
            sum.setTotalAmount(Double.parseDouble(String.valueOf(t.get("totalAmount"))));
            sum.setAmount(Double.parseDouble(String.valueOf(t.get("amount"))));
            sum.setNotAmount(Double.parseDouble(String.valueOf(t.get("notAmount"))));
            list.add(sum);
        });
        return list;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int uploadInvoiceExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),InvoiceDto.class,easyExcelConfig)
                    .sheet()  //excel中表的名称，默认为第一个sheet
                    .doRead();
            List<InvoiceDto> data = easyExcelConfig.getData();
            String[] ids = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                InvoiceDto in=data.get(i);
                ids[i] = in.getInvoice_belongs_to_the_contract_order().split("-")[0];
                in.setTax_not_included(in.getTotal_amount() / (1 + in.getTax_rate()*0.01));
                in.setAmount_of_tax_payable(in.getTotal_amount() - in.getTax_not_included());
            }
            int result=invoiceDao.insertInvoiceList(data);
            contractDao.computing();
            contractDao.updateTicketAmountReceived(ids);
            return result;
        } catch (Exception e) {
            throw new BusinessException("数据异常");
        }
    }
}
