package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName：InvoiceDto
 *
 * @author:l
 * @Date: 2024/7/31
 * @Description:
 * @version: 1.0
 */
@Data
public class InvoiceDto {
    @ExcelProperty(value="发票所属合同订单",index = 0)
    @ColumnWidth(30)
    private String invoice_belongs_to_the_contract_order;

    @ExcelProperty(value="开票日期",index = 1)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date_of_drawing;

    @ExcelProperty(value="收到日期",index = 2)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date_received;

    @ExcelProperty(value="发票抬头",index = 3)
    @ColumnWidth(20)
    private String invoice_title;

    @ExcelProperty(value="发票类型",index = 4)
    @ColumnWidth(20)
    private String invoice_type;

    @ExcelProperty(value="发票代码",index = 5)
    @ColumnWidth(20)
    private String invoice_code;

    @ExcelProperty(value="发票号码",index = 6)
    @ColumnWidth(20)
    private String invoice_number;

    @ExcelProperty(value="开票公司名称",index = 7)
    @ColumnWidth(20)
    private String billing_company;

    @ExcelProperty(value="内容",index = 8)
    @ColumnWidth(20)
    private String content;

    @ExcelProperty(value="总金额",index = 9)
    @ColumnWidth(20)
    private double total_amount;

    @ExcelProperty(value="税率",index = 10)
    @ColumnWidth(20)
    private double tax_rate;

    @ExcelProperty(value="不含税金额")
    @ColumnWidth(20)
    private double tax_not_included;

    @ExcelProperty(value="税额")
    @ColumnWidth(20)
    private double amount_of_tax_payable;

    @ExcelProperty(value="开票来源",index = 11)
    @ColumnWidth(20)
    private String billing_source;

    @ExcelProperty(value="交会计年份",index = 12)
    @ColumnWidth(20)
    private int accounting_year;

    @ExcelProperty(value="交会计月份",index = 13)
    @ColumnWidth(20)
    private int accounting_month;

    @ExcelProperty(value="备注",index = 14)
    @ColumnWidth(20)
    private String remark2;
}
