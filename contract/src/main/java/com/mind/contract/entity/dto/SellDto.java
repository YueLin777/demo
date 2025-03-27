package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mind.contract.entity.query.BaseParam;
import lombok.Data;

import java.util.Date;

/**
 * ClassName：SellDto
 *
 * @author:l
 * @Date: 2024/8/5
 * @Description:
 * @version: 1.0
 */
@Data
public class SellDto{
    @ExcelProperty(value = "合同号",index = 0)
    @ColumnWidth(30)
    private String contractId;

    @ExcelProperty(value = "日期",index = 1)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value = "公司",index = 2)
    @ColumnWidth(20)
    private String company;

    @ExcelProperty(value = "有无纸质双方盖章",index = 3)
    @ColumnWidth(10)
    private String seal;

    @ExcelProperty(value = "档案编号",index = 4)
    @ColumnWidth(20)
    private String file;

    @ExcelProperty(value = "负责人",index = 5)
    @ColumnWidth(20)
    private String person;

    @ExcelProperty(value = "客户",index = 6)
    @ColumnWidth(20)
    private String customer;

    @ExcelProperty(value = "合同金额",index = 7)
    @ColumnWidth(20)
    private double money;

    @ExcelProperty(value = "开票状态",index = 8)
    @ColumnWidth(20)
    private String billingStatus;

    @ExcelProperty(value = "交付状态",index = 9)
    @ColumnWidth(20)
    private String deliveryStatus;

    @ExcelProperty("已开票金额")
    @ColumnWidth(20)
    private double invoicedAmount;

    @ExcelProperty("未开票金额")
    @ColumnWidth(20)
    private double invoiceNotAmount;

    @ExcelProperty(value = "产品名称",index = 10)
    @ColumnWidth(20)
    private String product;

    @ExcelProperty(value = "合同详情",index = 11)
    @ColumnWidth(20)
    private String detail;

    @ExcelProperty(value = "收款1",index = 12)
    @ColumnWidth(20)
    private double collection1;

    @ExcelProperty(value = "收款2",index = 13)
    @ColumnWidth(20)
    private double collection2;

    @ExcelProperty(value = "收款3",index = 14)
    @ColumnWidth(20)
    private double collection3;

    @ExcelProperty(value = "收款4",index = 15)
    @ColumnWidth(20)
    private double collection4;

    @ExcelProperty("已付款")
    @ColumnWidth(20)
    private double receivedCollection;

    @ExcelProperty("剩余应付款")
    @ColumnWidth(20)
    private double residualCollection;

    @ExcelProperty(value = "状态",index = 16)
    @ColumnWidth(20)
    private String state;

    @ExcelProperty(value = "备注",index = 17)
    @ColumnWidth(20)
    private String remark;
}
