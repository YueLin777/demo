package com.mind.contract.controller;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.pojo.*;
import com.mind.contract.entity.query.*;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.ContractService;
import com.mind.contract.utils.EasyExcelConfig;
import com.mind.contract.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/contract")
public class ContractController {


    @Resource
    private ContractService contractService;

    @RequestMapping("/test")
    public ResponseVo test() {
        return ResponseVo.ok();
    }

    //采购合同填写
    @RequestMapping("/purchase")
    public ResponseVo purchase(@RequestBody Contract contract, Invoice invoice){
        ResponseVo responseVo = contractService.write(contract,invoice);
        return ResponseVo.ok(responseVo);
    }

    //采购合同修改
    @RequestMapping("/modify_purchase")
    public ResponseVo modify_purchase(@RequestBody Contract contract,Invoice invoice){
        if (StringTools.isEmpty(contract.getContractId())){
            throw new BusinessException("合同号不能为空");
        }

        ResponseVo responseVo = contractService.modify_purchase(contract,invoice);
        return ResponseVo.ok(responseVo);
    }

    //删除采购合同
    @RequestMapping("/delpurchase/{contractId}")
    public ResponseVo delpurchase(@PathVariable("contractId") String contractId){
        if (null == contractId){
            throw new BusinessException("contractId 不能为空");
        }

        Integer i = contractService.getContract(contractId);
        if(i == 0){
            throw new BusinessException("合同不存在");
        }

        return contractService.delpurchase(contractId);
    }

    //查询采购合同
    @RequestMapping("/query_purchase")
    public ResponseVo query_purchase(@RequestBody(required = false) ContractQuery contractQuery){
        PaginationResultVo<Contract> resultVo = contractService.query_purchase(contractQuery);
        return ResponseVo.ok(resultVo);
    }

    //上传采购合同文件
    @PostMapping("/upload/file")
    public ResponseVo upload(@RequestBody MultipartFile file, String id){
        return ResponseVo.ok(contractService.uploadFile(file,id));
    }

    //删除采购合同文件
    @DeleteMapping("/del/file/{id}")
    public ResponseVo delFile(@NotBlank(message = "合同id不能为空") @PathVariable("id") String id){
        return ResponseVo.ok(contractService.delFile(id));
    }

    //填写进项发票明细
    @RequestMapping("/invoice")
    public ResponseVo invoice(@RequestBody Invoice invoice,Contract contract){
        ResponseVo responseVo = contractService.fill(invoice,contract);
        return ResponseVo.ok(responseVo);
    }

    //修改进项发票明细
    @RequestMapping("/modify_invoice")
    public ResponseVo modify_invoice(@RequestBody Invoice invoice,Contract contract){

        ResponseVo responseVo = contractService.modify_invoice(invoice,contract);
        return ResponseVo.ok(responseVo);
    }

    //删除进项发票明细
    @RequestMapping("/delinvoice/{invoice_code}/{invoice_number}")
    public ResponseVo delinvoice (@PathVariable("invoice_code") String invoice_code, @PathVariable("invoice_number") String invoice_number){
        if (StringTools.isEmpty(invoice_code)) {
            throw new BusinessException("发票代码不能为空");
        }
        if (StringTools.isEmpty(invoice_number)) {
            throw new BusinessException("发票号码不能为空");
        }
        int i = contractService.selectInvoiceId(invoice_code, invoice_number);
        if (i == 0) {
            throw new BusinessException("发票所属合同订单不存在");
        }
        return contractService.delinvoice(invoice_code, invoice_number);
    }

    //查询进项发票明细
    @RequestMapping("/query_invoice")
    public ResponseVo query_invoice(@RequestBody(required = false) InvoiceQuery invoiceQuery){
        PaginationResultVo<Invoice> resultVo = contractService.query_invoice(invoiceQuery);
        return ResponseVo.ok(resultVo);
    }

    //上传进项发票明细文件
    @PostMapping("/uploadInvoice/file")
    public ResponseVo uploadInvoice(@RequestBody MultipartFile file, String invoice_code,String invoice_number){
        return ResponseVo.ok(contractService.uploadInvoice(file,invoice_code,invoice_number));
    }

    //删除进项发票明细文件
    @DeleteMapping("/delInvoice/file/{invoice_code}/{invoice_number}")
    public ResponseVo delInvoice(@NotBlank(message = "发票不能为空") @PathVariable("invoice_code") String invoice_code,@PathVariable("invoice_number") String invoice_number){
        return ResponseVo.ok(contractService.delInvoice(invoice_code,invoice_number));
    }

    //填写收入支出报表
    @RequestMapping("/statement")
    public ResponseVo statement(@RequestBody Statement statement){
        ResponseVo responseVo = contractService.statement(statement);
        return ResponseVo.ok(responseVo);
    }

    //修改收入支出报表
   @RequestMapping("/modify_statement")
   public ResponseVo modify_statement(@RequestBody Statement statement){
        if (null == statement.getId()){
            throw new BusinessException("id不能为空");
        }

       ResponseVo responseVo = contractService.modify_statement(statement);
       return ResponseVo.ok(responseVo);
   }

    //删除收入支出报表
    @RequestMapping("/del_statement/{id}")
    public ResponseVo del_statement(@PathVariable("id") Integer id){
        if (null == id){
            throw new BusinessException("编号不能为空");
        }
        ResponseVo responseVo = contractService.del_statement(id);
        return ResponseVo.ok(responseVo);
    }

    //查询收入支出报表
    @RequestMapping("/query_statement")
    public ResponseVo query_statement(@RequestBody(required = false) StatementQuery statementQuery){
        PaginationResultVo<Statement> resultVo = contractService.query_statement(statementQuery);
        return ResponseVo.ok(resultVo);
    }

    //按账户去求和
    @RequestMapping("/accountSum")
    public ResponseVo accountSum(@RequestBody AccountSum accountSum){
        List<AccountSum> accountSums = contractService.accountSum(accountSum);
        return  ResponseVo.ok(accountSums);
    }

    /**
     * 导出
     */
    @RequestMapping("/exportContract")
    @ResponseBody
    public void export(HttpServletResponse httpServletResponse){
        contractService.exportContract(httpServletResponse);
    }

    @PostMapping("/uploadContractExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        contractService.uploadContractExcel(file);
        return ResponseVo.ok();
    }

}