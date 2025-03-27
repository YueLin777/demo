package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.SellDao;
import com.mind.contract.dao.SellInvoiceDao;
import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.dto.InvoiceDto;
import com.mind.contract.entity.dto.SellDto;
import com.mind.contract.entity.enums.PageSize;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.pojo.Statement;
import com.mind.contract.entity.pojo.TotalSum;
import com.mind.contract.entity.query.SellQuery;
import com.mind.contract.entity.query.SimplePage;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.SellInvoiceService;
import com.mind.contract.service.SellService;
import com.mind.contract.utils.EasyExcelConfig;
import com.mind.contract.utils.FileUtil;
import com.mysql.cj.util.StringUtils;
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
import java.util.UUID;

/**
 * ClassName：SellService
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
/* 业务接口实现*/
@Service
@Slf4j
public class SellServiceImpl implements SellService {
    @Value("${file.save.xsPath}")
    private String savexsPath;
    @Resource
    private SellDao sellDao;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private EasyExcelConfig<SellDto> easyExcelConfig;

    @Override
    public ResponseVo sell(Sell sell, SellInvoice sellInvoice) {
        int num = 0;
        //判断合同号是否重复
        Integer i = sellDao.repeat(sell.getContractId());
        if (i == 0){
            num = sellDao.sell(sell);
            //计算已收款
            sellDao.calculateCollection(sell);
            //计算剩余应付款
            sellDao.calculateNoCollection(sell);
            //计算已开票金额
            sellDao.calculateInvoiced(sell);
            //计算未开票金额
            sellDao.calculateNoInvoiced(sell);
        }else {
            throw new BusinessException("合同号不能重复");
        }
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modifySell(Sell sell, SellInvoice sellInvoice) {
        int num = 0;
        Integer n = sellDao.repeat(sell.getContractId());
        if (n == 0){
            num = sellDao.modifySell(sell);
        }else {
            String old = sellDao.selectOld(sell.getId());
            if (old.equals(sell.getContractId())){
                num = sellDao.modifySell(sell);
            }else {
                throw new BusinessException("合同号不能重复");
            }
        }
        //计算已收款
        sellDao.calculateCollection(sell);
        //计算剩余应付款
        sellDao.calculateNoCollection(sell);
        //计算已开票金额
        sellDao.calculateInvoiced(sell);
        //计算未开票金额
        sellDao.calculateNoInvoiced(sell);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer count(Integer id) {
        return sellDao.count(id);
    }

    @Override
    public ResponseVo delSell(Integer id) {
        Integer i = sellDao.delSell(id);
        if (i > 0) {
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVo<Sell> querySell(SellQuery sellQuery) {
        if(StringUtils.isNullOrEmpty(sellQuery.getContractIdFuzzy()) && !StringUtils.isNullOrEmpty(sellQuery.getResidualCollectionFuzzy())){
            sellQuery.setContractIdFuzzy(UUID.randomUUID().toString());
        }
        if(!StringUtils.isNullOrEmpty(sellQuery.getContractIdFuzzy()) && StringUtils.isNullOrEmpty(sellQuery.getResidualCollectionFuzzy())){
            sellQuery.setResidualCollectionFuzzy(Integer.MAX_VALUE+"");
        }

        //查询总记录数
        int count = findCountById(sellQuery);
        //每页显示数量
        int pageSize = sellQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : sellQuery.getPageSize();

        SimplePage simplePage = new SimplePage(sellQuery.getPageNo(),count,pageSize);
        sellQuery.setSimplePage(simplePage);

        List<Sell> sells = findListSell(sellQuery);

        PaginationResultVo<Sell> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),sells);
        return resultVo;
    }

    @Override
    public void exportSell(HttpServletResponse httpServletResponse) {
        try {
            List<SellDto> list = sellExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "销售合同", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), SellDto.class)
                    .sheet("销售合同").doWrite(list);
        } catch (Exception e) {
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    public ResponseVo uploadSell(MultipartFile file, Integer id) {
        Sell sell = sellDao.selectSid(id);
        if (sell == null){
            throw new BusinessException("发票id不存在");
        }

        String originalFilename = file.getOriginalFilename();

        String fileName = id + originalFilename.substring(originalFilename.lastIndexOf("."));

        fileUtil.saveFile(savexsPath,file,fileName);

        sell.setHas_file(1);
        sellDao.updateSell(sell);
        return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo delSellFile(Integer id) {
        Sell sell = sellDao.selectSid(id);
        if (sell == null){
            throw new BusinessException("销售合同id不存在");
        }
        fileUtil.deleteFile(savexsPath+"\\"+id+".pdf");
        sell.setHas_file(0);
        sellDao.updateSell(sell);
        return ResponseVo.ok();
    }

    @Override
    public TotalSum totalSum(TotalSum totalSum) {
        sellDao.totalSum();
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadSellExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),SellDto.class,easyExcelConfig)
                    .sheet()
                    .doRead();
            List<SellDto> data = easyExcelConfig.getData();
            data.forEach(con->{
                con.setReceivedCollection(con.getCollection1()+con.getCollection2()+con.getCollection3()+con.getCollection4());
                con.setResidualCollection(con.getMoney() - con.getReceivedCollection());
                Double sum = sellDao.getSumTicket(con.getContractId());
                con.setInvoicedAmount(sum==null?0:sum);
                con.setInvoiceNotAmount(con.getMoney()-con.getInvoicedAmount());
            });
            int result = sellDao.insertSellList(data);
            return result;
        } catch (Exception e) {
            throw new BusinessException("数据异常");
        }
    }

    private List<SellDto> sellExportList() {
        return sellDao.sellExportList();
    }

    private int findCountById(SellQuery sellQuery) {
        return sellDao.findCountById(sellQuery);
    }

    private List<Sell> findListSell(SellQuery sellQuery) {
        return sellDao.findListSell(sellQuery);
    }
}
