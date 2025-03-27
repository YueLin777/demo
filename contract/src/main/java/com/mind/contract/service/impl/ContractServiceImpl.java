package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.ContractDao;
import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.dto.InvoiceDto;
import com.mind.contract.entity.enums.PageSize;
import com.mind.contract.entity.pojo.*;
import com.mind.contract.entity.query.*;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.ContractService;
import com.mind.contract.utils.EasyExcelConfig;
import com.mind.contract.utils.FileUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/* 业务接口实现*/
@Service
@Slf4j
public class ContractServiceImpl implements ContractService{
    @Value("${file.save.path}")
    private String savePath;
    @Value("${file.save.zpPath}")
    private String savezpPath;

    @Autowired
    private EasyExcelConfig<ContractDto> easyExcelConfig;
    @Resource
    private ContractDao contractDao;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public ResponseVo write(Contract contract, Invoice invoice) {
        int num = 0;
        int count = contractDao.selectCount(contract.getContractId());
        if (count == 0){
            num = contractDao.write(contract);
            contractDao.computingPaid(contract);
            contractDao.computingRemain(contract);
            contractDao.computing();
            //未收票
            contractDao.computingNot(contract);
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
    public ResponseVo modify_purchase(Contract contract, Invoice invoice) {
        int num = 0;
        int count = contractDao.selectCount(contract.getContractId());
        if (count == 0){
            num = contractDao.update(contract);
        }else {
            String old = contractDao.selectContractOld(contract);
            if (old.equals(contract.getContractId())){
                num = contractDao.update(contract);
            }else {
                throw new BusinessException("合同号不能重复");
            }
        }
        contractDao.computingPaid(contract);
        contractDao.computingRemain(contract);
        contractDao.computing();
        contractDao.computingNot(contract);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer getContract(String contractId) {
        return contractDao.selectCount(contractId);
    }

    @Override
    public ResponseVo delpurchase(String contractId) {
        Integer i = contractDao.delpurchase(contractId);
        if (i > 0) {
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public ResponseVo fill(Invoice invoice,Contract contract) {
         //判断是否重复（基于复合主键 invoice_code 和 invoice_number）
        int num = 0;
        int count = contractDao.selectOrder(invoice.getInvoice_code(),invoice.getInvoice_number());
        if (count == 0){
            num = contractDao.writeInvoice(invoice);
            //总金额更新，合同的已收票金额和未收票金额发生改变
            contractDao.computing();
            String s = extractContractId(invoice);
            contract.setContractId(s);
            contractDao.computingNot(contract);
            //计算税额
            contractDao.calculate_amount(invoice);
            //计算不含税金额
            contractDao.calculate_no_amount(invoice);
        }else {
            throw new BusinessException("不能重复");
        }

        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modify_invoice(Invoice invoice, Contract contract) {
        int m = 0;
        int count = contractDao.selectOrder(invoice.getInvoice_code(),invoice.getInvoice_number());
        if (count == 1){
            m = contractDao.updateInvoice(invoice,contract);
        }
        //总金额更新，合同的已收票金额和未收票金额发生改变
        contractDao.computing();
        String s = extractContractId(invoice);
        contract.setContractId(s);
        contractDao.computingNot(contract);
        //计算税额
        contractDao.calculate_amount(invoice);
        //计算不含税金额
        contractDao.calculate_no_amount(invoice);
        //判断是否成功
        if (m > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer selectInvoiceId(String invoice_code,String invoice_number) {
         return contractDao.selectOrder(invoice_code,invoice_number);
    }

    @Override
    public ResponseVo delinvoice(String invoice_code,String invoice_number) {
        Invoice invoice=contractDao.queryInvice(invoice_code,invoice_number);
        String totalId = invoice.getInvoice_belongs_to_the_contract_order().split("-")[0];
        Contract contract=contractDao.queryContract(totalId);
        if (contract!=null){
            contract.setTicket_amount_received(contract.getTicket_amount_received()-invoice.getTotal_amount());
            contract.setTicket_amount_not_collected(contract.getContract_amount()-contract.getTicket_amount_received());
            contractDao.updateContract(contract);
        }
        Integer i = contractDao.delinvoice(invoice_code,invoice_number);
        if (i > 0) {
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public ResponseVo statement(Statement statement) {
        int num;
        int count = contractDao.selectStatementId(statement.getOrder());
        if (count == 0){
            num = contractDao.write_statement(statement);
        }else {
            throw new BusinessException("订单号不能重复");
        }
        //计算结余
        contractDao.calculate_balance(statement);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modify_statement(Statement statement) {
        int m = 0;
        int count = contractDao.selectStatementId(statement.getOrder());
        if (count == 0){
            m = contractDao.modify_statement(statement);
        }else {
            String old = contractDao.selectStatementOld(statement);
            if (old.equals(statement.getOrder())){
                m = contractDao.modify_statement(statement);
            }else {
                throw new BusinessException("没有符合的信息");
            }
        }
        //计算结余
        contractDao.calculate_balance(statement);
        //判断是否成功
        if (m > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo del_statement(Integer id) {
        Integer i = contractDao.del_statement(id);
        if (i > 0) {
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVo<Contract> query_purchase(ContractQuery contractQuery) {
        //如果条件参数为空，则生成一个随机不存在的数，避免影响查询结果
        if (StringUtils.isNullOrEmpty(contractQuery.getContractIdFuzzy()) && !StringUtils.isNullOrEmpty(contractQuery.getCorresponding_itemFuzzy())){
            contractQuery.setContractIdFuzzy(UUID.randomUUID().toString());
        }
        if(!StringUtils.isNullOrEmpty(contractQuery.getContractIdFuzzy()) && StringUtils.isNullOrEmpty(contractQuery.getCorresponding_itemFuzzy())){
            contractQuery.setCorresponding_itemFuzzy(Integer.MAX_VALUE+"");
        }
        //查询总记录数
        int count = findCountByContractId(contractQuery);
        //每页显示数量
        int pageSize = contractQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : contractQuery.getPageSize();

        SimplePage simplePage = new SimplePage(contractQuery.getPageNo(),count,pageSize);
        contractQuery.setSimplePage(simplePage);

        List<Contract> contracts = findListContract(contractQuery);

        PaginationResultVo<Contract> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),contracts);
        return resultVo;
    }

    @Override
    public PaginationResultVo<Invoice> query_invoice(InvoiceQuery invoiceQuery) {
        //查询总记录数
        int count = findCountByInvoiceId(invoiceQuery);
        //每页显示数量
        int pageSize = invoiceQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : invoiceQuery.getPageSize();

        SimplePage simplePage = new SimplePage(invoiceQuery.getPageNo(),count,pageSize);
        invoiceQuery.setSimplePage(simplePage);

        List<Invoice> invoices = findListByInvoiceId(invoiceQuery);
        PaginationResultVo<Invoice> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),invoices);
        return resultVo;
    }

    @Override
    public PaginationResultVo<Statement> query_statement(StatementQuery statementQuery) {
        //查询总记录数
        int count = findCountId(statementQuery);
        //每页显示数量
        int pageSize = statementQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : statementQuery.getPageSize();

        SimplePage simplePage = new SimplePage(statementQuery.getPageNo(),count,pageSize);
        statementQuery.setSimplePage(simplePage);
        List<Statement> contracts = findListId(statementQuery);
        PaginationResultVo<Statement> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),contracts);
        return resultVo;
    }

    @Override
    public List<AccountSum> accountSum(AccountSum accountSum) {
        List<AccountSum> accountSums = contractDao.accountSum(accountSum.getAccount());
        return accountSums;
    }

    @Override
    public void exportContract(HttpServletResponse httpServletResponse) {
        try {
            List<ContractDto> contractDtos = statementExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "采购合同", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), ContractDto.class)
                    .sheet("采购合同").doWrite(contractDtos);
        } catch (Exception e) {
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    public ResponseVo uploadFile(MultipartFile file, String id) {
        Contract contract = contractDao.selectById(id);

        if(contract==null){
            throw new RuntimeException("合同id不存在");
        }

        String originalFilename = file.getOriginalFilename();

        String fileName = id+ originalFilename.substring(originalFilename.lastIndexOf("."));

        fileUtil.saveFile(savePath,file,fileName);

        //更新,是否有合同改为有合同
        contract.setHas_file(1);
        contractDao.update(contract);

        return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo delFile(String id) {
        Contract contract = contractDao.selectById(id);

        if(contract==null){
            throw new RuntimeException("合同id不存在");
        }

        fileUtil.deleteFile(savePath+"\\"+id+".pdf");
        //更新,是否有合同改为无合同
        contract.setHas_file(0);
        contractDao.update(contract);

        return ResponseVo.ok();
    }

    @Override
    public ResponseVo uploadInvoice(MultipartFile file, String invoice_code,String invoice_number) {
        Invoice invoice  = contractDao.selectSid(invoice_code,invoice_number);
        if (invoice == null){
            throw new BusinessException("发票id不存在");
        }

        String originalFilename = file.getOriginalFilename();

        String fileName = invoice_code + invoice_number + originalFilename.substring(originalFilename.lastIndexOf("."));

        fileUtil.saveFile(savezpPath,file,fileName);

        invoice.setHas_file(1);
        contractDao.updateInvoiceFile(invoice);

        return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo delInvoice(String invoice_code,String invoice_number) {
        Invoice invoice = contractDao.selectSid(invoice_code,invoice_number);
        if (invoice == null){
            throw new BusinessException("发票id不存在");
        }
        fileUtil.deleteFile(savezpPath+"\\"+invoice_number+invoice_number+".pdf");
        invoice.setHas_file(0);
        contractDao.updateInvoiceFile(invoice);
        return ResponseVo.ok();
    }

    @Override
    public int uploadContractExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),ContractDto.class,easyExcelConfig)
                    .sheet()  //excel中表的名称，默认为第一个sheet
                    .doRead();
            List<ContractDto> data = easyExcelConfig.getData();
            //将读取到的数据存入数据库中
            data.forEach(con->{
                con.setPaid(con.getPayment1()+con.getPayment2()+con.getPayment3()+con.getPayment4());
                con.setRemain_paid(con.getContract_amount()-con.getPaid());
                Double sum=contractDao.getSumTicket(con.getContractId());
                con.setTicket_amount_received(sum==null?0:sum);
                con.setTicket_amount_not_collected(con.getContract_amount()-con.getTicket_amount_received());
            });
            int result=contractDao.insertContractList(data);
            return result;
        } catch (Exception e) {
            throw new BusinessException("数据异常");
        }
    }

    private List<ContractDto> statementExportList() {
        List<Contract> list = contractDao.selectAllContract();
        ArrayList<ContractDto> resultList = new ArrayList<>();
        for (Contract contract : list){
            ContractDto contractDto = new ContractDto();
            BeanUtils.copyProperties(contract,contractDto);
            resultList.add(contractDto);
        }
        return resultList;
    }

    private List<Statement> findListId(StatementQuery statementQuery) {
        return contractDao.findListId(statementQuery);
    }

    private int findCountId(StatementQuery statementQuery) {
        return contractDao.findCountId(statementQuery);
    }

    private List<Invoice> findListByInvoiceId(InvoiceQuery invoiceQuery) {
        return contractDao.findListByInvoiceId(invoiceQuery);
    }

    private int findCountByInvoiceId(InvoiceQuery invoiceQuery) {
        return contractDao.findCountByInvoiceId(invoiceQuery);
    }

    private List<Contract> findListContract(ContractQuery contractQuery) {
        return contractDao.selectListContract(contractQuery);
    }

    private int findCountByContractId(ContractQuery contractQuery) {
        return contractDao.findCountByContractId(contractQuery);
    }

    public static String extractContractId(Invoice invoice) {

        // 使用String的indexOf方法找到第一个"-"的位置
        int dashIndex = invoice.getInvoice_belongs_to_the_contract_order().indexOf('-');

        // 如果找不到"-"，则返回整个字符串（这里假设整个字符串就是合同号）
        // 或者，你可以根据需求返回null或抛出异常
        if (dashIndex == -1) {
            return invoice.getInvoice_belongs_to_the_contract_order();
        }

        // 否则，使用substring方法提取"-"之前的部分
        return invoice.getInvoice_belongs_to_the_contract_order().substring(0, dashIndex);
    }
}
