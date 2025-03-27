package com.mind.contract.dao;

import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.pojo.*;
import com.mind.contract.entity.query.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


/*
* 数据库接口层
* */
@Repository
public interface ContractDao {

    Integer selectCount(String contractId);

    Integer write(Contract contract);

    String selectContractOld(Contract contract);

    Integer update(Contract contract);

    Integer delpurchase(String contractId);

    Integer computing();

    Integer computingPaid(Contract contract);

    Integer computingRemain(Contract contract);

    Integer computingNot(Contract contract);
    Integer updateTicketAmountReceived(String[] ids);

    Integer selectOrder(String invoice_code,String invoice_number);

    Integer writeInvoice(Invoice invoice);

    Integer updateInvoice(@Param("invoice") Invoice invoice, @Param("contract") Contract contract);

    Integer delinvoice(String invoice_code,String invoice_number);

    Integer selectStatementId(String order);

    String selectStatementOld(Statement statement);

    Integer write_statement(Statement statement);

    Integer calculate_balance(Statement statement);

    Integer modify_statement(Statement statement);

    Integer del_statement(Integer id);

    List<Statement> findListId(@Param("query") StatementQuery statementQuery);

    Integer findCountId(@Param("query") StatementQuery statementQuery);

    Integer findCountByInvoiceId(@Param("query") InvoiceQuery invoiceQuery);

    List<Invoice> findListByInvoiceId(@Param("query") InvoiceQuery invoiceQuery);

    Integer calculate_amount(Invoice invoice);

    Integer calculate_no_amount(Invoice invoice);

    Integer findCountByContractId(@Param("query") ContractQuery contractQuery);

    List<Contract> selectListContract(@Param("query") ContractQuery contractQuery);

    Invoice queryInvice(@Param("invoiceCode") String invoiceCode,@Param("invoiceNumber") String invoiceNumber);

    Contract queryContract(String contractId);

    Integer updateContract(Contract contract);

    List<AccountSum> accountSum(String account);

    List<Contract> selectAllContract();

    Contract selectById(String id);

    Invoice selectSid(String invoice_code, String invoice_number);

    Integer updateInvoiceFile(Invoice invoice);

    Double getSumTicket(String contractId);

    int insertContractList(List<ContractDto> data);
}
