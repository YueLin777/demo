package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SellInvoiceDto {
    @ExcelProperty(value = "对应合同号/订单号",index = 0)
    @ColumnWidth(30)
    private String contractId;

    @ExcelProperty(value = "公司",index = 1)
    @ColumnWidth(20)
    private String company;

    @ExcelProperty(value = "日期",index = 2)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value = "发票号码",index = 3)
    @ColumnWidth(20)
    private String invoiceNumber;

    @ExcelProperty(value = "客户名称",index = 4)
    @ColumnWidth(30)
    private String customerName;

    @ExcelProperty(value = "纳税人识别号",index = 5)
    @ColumnWidth(30)
    private String ratepayer;

    @ExcelProperty(value = "地址",index = 6)
    @ColumnWidth(30)
    private String position;

    @ExcelProperty(value = "公司电话",index = 7)
    @ColumnWidth(30)
    private String phone;

    @ExcelProperty(value = "开户行及账号",index = 8)
    @ColumnWidth(30)
    private String account;

    @ExcelProperty(value = "货物名称",index = 9)
    @ColumnWidth(30)
    private String cargoName;

    @ExcelProperty(value = "开票金额",index = 10)
    @ColumnWidth(30)
    private double invoiceAmount;

    @ExcelProperty(value = "税率",index = 11)
    @ColumnWidth(30)
    private double taxRate;

    @ExcelProperty("税额")
    @ColumnWidth(30)
    private double taxAmount;

    @ExcelProperty("不含税金额")
    @ColumnWidth(30)
    private double taxNotAmount;
}
