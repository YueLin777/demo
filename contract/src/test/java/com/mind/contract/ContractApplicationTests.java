//package com.mind.contract;
//
//import com.mind.contract.entity.pojo.*;
//import com.mind.contract.entity.query.InvoiceQuery;
//import com.mind.contract.service.ContractService;
//import com.mind.contract.service.InvoiceService;
//import com.mind.contract.service.StatementService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//class ContractApplicationTests {
//
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private ContractService contractService;
//    @Autowired
//    private StatementService statementService;
//    @Autowired
//    private InvoiceService invoiceService;
//    @Test
//    void contextLoads() throws SQLException {
//        Connection connection = dataSource.getConnection();
//        System.out.println(connection);
//        connection.close();
//    }
//
//    @Test
//    void write(){
//        Contract contract = new Contract();
//        Invoice invoice = new Invoice();
//        contract.setContractId("1");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.JULY, 18); // 注意月份是从0开始的，所以7月是Calendar.JULY
//        contract.setDate(calendar.getTime()); // 假设 setDate 接受 Date 类型
//        contractService.modify_purchase(contract,invoice);
//    }
//
//
//    @Test
//    void delpurchase(){
//        contractService.delpurchase("3");
//    }
//
//    @Test
//    void fill(){
//        Invoice invoice = new Invoice();
//        Contract contract = new Contract();
//        invoice.setInvoice_belongs_to_the_contract_order("1-35-4-4");
//        invoice.setTotal_amount(342.34);
//        contractService.fill(invoice,contract);
//    }
//
//    @Test
//    void statement(){
//        Statement statement = new Statement();
//        statement.setIncome(234.4);
//        statement.setDisburse(121.2);
//        contractService.statement(statement);
//    }
//
//    @Test
//    void modify_statement(){
//        Statement statement = new Statement();
//        statement.setId(2);
//        statement.setIncome(345.34);
//        contractService.modify_statement(statement);
//    }
//
//    @Test
//    void del_statement(){
//        contractService.del_statement(3);
//    }
//
//
//    @Test
//    void query(){
//        InvoiceQuery invoiceQuery = new InvoiceQuery();
//        invoiceQuery.setInvoice_belongs_to_the_contract_orderFuzzy("1");
//    }
//
////    @Test
////    void period(){
//////        StatementSummary summary = new StatementSummary();
//////        PeriodQuery periodQuery = new PeriodQuery();
//////        periodQuery.setStartDate(LocalDate.parse("2024-07-09"));
//////        periodQuery.setEndDate(LocalDate.parse("2024-07-20"));
//////        StatementSummary statementSummary = contractService.time_period(summary,periodQuery);
//////        System.out.println(statementSummary);
////    }
//
//    @Test
//    void rootPath(){
//        String rootPath = System.getProperty("user.dir");
//        System.out.println(rootPath);
//        /*C:\Users\ASUS\Desktop\contract*/
//    }
//
//    @Test
//    void accountSum(){
//        AccountSum accountSum = new AccountSum();
//        List<AccountSum> accountSums = contractService.accountSum(accountSum);
//        System.out.println(accountSums);
//    }
//
//    @Test
//    void timeSum(){
//        TimeSum timeSum = new TimeSum();
//        timeSum.setYear("2024");
//        timeSum.setMonth("7");
//        TimeSum timeSum1 = invoiceService.timeSum(timeSum);
//        System.out.println(timeSum1);
//    }
//
//}
