package com.mind.contract.service;

import com.mind.contract.entity.pojo.*;
import com.mind.contract.entity.query.*;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
* 业务接口
* */
public interface ContractService {

    ResponseVo write(Contract contract, Invoice invoice);

    Integer getContract(String contractId);

    ResponseVo delpurchase(String contractId);

    ResponseVo fill(Invoice invoice,Contract contract);

    Integer selectInvoiceId(String invoice_code,String invoice_number);

    ResponseVo delinvoice(String invoice_code,String invoice_number);

    ResponseVo statement(Statement statement);

    PaginationResultVo<Contract> query_purchase(ContractQuery contractQuery);

    PaginationResultVo<Invoice> query_invoice(InvoiceQuery invoiceQuery);

    PaginationResultVo<Statement> query_statement(StatementQuery statementQuery);

    ResponseVo modify_invoice(Invoice invoice, Contract contract);

    ResponseVo modify_purchase(Contract contract, Invoice invoice);

    ResponseVo modify_statement(Statement statement);

    ResponseVo del_statement(Integer id);

    List<AccountSum> accountSum(AccountSum accountSum);

    void exportContract(HttpServletResponse httpServletResponse);

    ResponseVo uploadFile(MultipartFile file, String id);

    ResponseVo delFile(String id);

    ResponseVo uploadInvoice(MultipartFile file , String invoice_code,String invoice_number);

    ResponseVo delInvoice(String invoice_code,String invoice_number);

    int uploadContractExcel(MultipartFile file);
}
