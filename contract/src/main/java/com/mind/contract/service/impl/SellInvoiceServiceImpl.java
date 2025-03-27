package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.SellDao;
import com.mind.contract.dao.SellInvoiceDao;
import com.mind.contract.entity.dto.InvoiceDto;
import com.mind.contract.entity.dto.SellDto;
import com.mind.contract.entity.dto.SellInvoiceDto;
import com.mind.contract.entity.enums.PageSize;
import com.mind.contract.entity.pojo.Contract;
import com.mind.contract.entity.pojo.Invoice;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.query.SellInvoiceQuery;
import com.mind.contract.entity.query.SellQuery;
import com.mind.contract.entity.query.SimplePage;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.SellInvoiceService;
import com.mind.contract.service.SellService;
import com.mind.contract.utils.EasyExcelConfig;
import com.mind.contract.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * ClassName：SellInvoiceImpl
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
/* 业务接口实现*/
@Service
@Slf4j
public class SellInvoiceServiceImpl implements SellInvoiceService {
    @Value("${file.save.xsfpPath}")
    private String savexsfpPath;
    @Resource
    private SellInvoiceDao sellInvoiceDao;
    @Autowired
    private EasyExcelConfig<SellInvoiceDto> easyExcelConfig;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public ResponseVo sellInvoice(SellInvoice sellInvoice, Sell sell) {
        int num = 0;
        //判断contractId是否重复
        Integer i = sellInvoiceDao.repeat(sellInvoice.getContractId());
        if (i == 0){
            num = sellInvoiceDao.sellInvoice(sellInvoice);
            //计算已开票金额
            sellInvoiceDao.calculateInvoiced(sell);
            sellInvoice=sellInvoiceDao.querySellInvoiceByCid(sellInvoice.getContractId());
            String sellId = sellInvoice.getContractId().split("-")[0];
            sell.setContractId(sellId);
            //计算未开票金额
            sellInvoiceDao.calculateNoInvoiced(sell);
            //计算税额
            sellInvoiceDao.calculateAmount(sellInvoice);
            //计算不含税金额
            sellInvoiceDao.calculateNoAmount(sellInvoice);
        }else {
            throw new BusinessException("不能重复");
        }
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modifySellInvoice(SellInvoice sellInvoice, Sell sell) {
        int num = 0;
        //判断contractId是否重复
        Integer n = sellInvoiceDao.repeat(sellInvoice.getContractId());
        if (n == 0){
                num = sellInvoiceDao.modifySellInvoice(sellInvoice);
        }else {
            String old = sellInvoiceDao.selectOld(sellInvoice.getId());
            if (old.equals(sellInvoice.getContractId())){
                num = sellInvoiceDao.modifySellInvoice(sellInvoice);
            }else {
                throw new BusinessException("合同号不能重复");
            }
        }
        //计算已开票金额
        sellInvoiceDao.calculateInvoiced(sell);
        String sellId = sellInvoice.getContractId().split("-")[0];
        sell.setContractId(sellId);
        //计算未开票金额
        sellInvoiceDao.calculateNoInvoiced(sell);
        //计算税额
        sellInvoiceDao.calculateAmount(sellInvoice);
        //计算不含税金额
        sellInvoiceDao.calculateNoAmount(sellInvoice);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer count(Integer id) {
        return sellInvoiceDao.count(id);
    }

    @Override
    public ResponseVo delSellInvoice(Integer id) {
        SellInvoice sellInvoice = sellInvoiceDao.querySellInvoice(id);
        String sellId = sellInvoice.getContractId().split("-")[0];
        Sell sell = sellInvoiceDao.querySell(sellId);
        if (sell!=null){
            sell.setReceivedCollection(sell.getReceivedCollection()-sellInvoice.getInvoiceAmount());
            sell.setResidualCollection(sell.getMoney()-sell.getReceivedCollection());
            sellInvoiceDao.updateSell(sell);
        }
        Integer i = sellInvoiceDao.delSellInvoice(id);
        if (i > 0) {
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVo<SellInvoice> querySellInvoice(SellInvoiceQuery sellInvoiceQuery) {
        //查询总记录数
        int count = findCountById(sellInvoiceQuery);
        //每页显示数量
        int pageSize = sellInvoiceQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : sellInvoiceQuery.getPageSize();

        SimplePage simplePage = new SimplePage(sellInvoiceQuery.getPageNo(),count,pageSize);
        sellInvoiceQuery.setSimplePage(simplePage);

        List<SellInvoice> sellInvoices = findListById(sellInvoiceQuery);
        PaginationResultVo<SellInvoice> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),sellInvoices);
        return resultVo;
    }

    @Override
    public void exportSellInvoice(HttpServletResponse httpServletResponse) {
        try {
            List<SellInvoiceDto> list = sellInvoiceExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "销售发票", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), SellInvoiceDto.class)
                    .sheet("销售发票").doWrite(list);
        } catch (Exception e) {
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    public ResponseVo uploadSellInvoice(MultipartFile file, Integer id) {
        SellInvoice sellInvoice = sellInvoiceDao.selectSid(id);
        if (sellInvoice == null){
            throw new BusinessException("销售发票id不存在");
        }
        String originalFilename = file.getOriginalFilename();

        String fileName = id + originalFilename.substring(originalFilename.lastIndexOf("."));

        fileUtil.saveFile(savexsfpPath,file,fileName);

        sellInvoice.setHas_file(1);
        sellInvoiceDao.updateSellInvoice(sellInvoice);
        return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo delSellInvoiceFile(Integer id) {
        SellInvoice sellInvoice = sellInvoiceDao.selectSid(id);
        if (sellInvoice == null){
            throw new BusinessException("销售发票id不存在");
        }
        fileUtil.deleteFile(savexsfpPath+"\\"+id+".pdf");
        sellInvoice.setHas_file(0);
        sellInvoiceDao.updateSellInvoice(sellInvoice);
        return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadSellInvoiceExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),SellInvoiceDto.class,easyExcelConfig)
                    .sheet()
                    .doRead();
            List<SellInvoiceDto> data = easyExcelConfig.getData();
            String[] ids = new String[data.size()];
            for (int i = 0;i < data.size();i++){
                SellInvoiceDto in = data.get(i);
                ids[i] = in.getContractId().split("-")[0];
                in.setTaxNotAmount(in.getInvoiceAmount() / (1+ in.getTaxRate()*0.01));
                in.setTaxAmount(in.getInvoiceAmount() - in.getTaxNotAmount());
            }
            int result = sellInvoiceDao.insertSellInvoiceList(data);
            sellInvoiceDao.invoiced();
            sellInvoiceDao.updateTicketAmountReceived(ids);
            return result;
        } catch (Exception e){
            throw new BusinessException("数据异常");
        }
    }

    private List<SellInvoiceDto> sellInvoiceExportList() {
        return sellInvoiceDao.sellInvoiceExportList();
    }

    private int findCountById(SellInvoiceQuery sellInvoiceQuery) {
        return sellInvoiceDao.findCountById(sellInvoiceQuery);
    }

    private List<SellInvoice> findListById(SellInvoiceQuery sellInvoiceQuery) {
        return sellInvoiceDao.findListById(sellInvoiceQuery);
    }
}
