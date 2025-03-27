package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName：StatementDto
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
@Data
public class StatementDto {
    @ExcelProperty("编号")
    @ColumnWidth(20)
    private Integer id;

    @ExcelProperty(value = "账户",index = 0)
    @ColumnWidth(20)
    private String account;

    @ExcelProperty(value = "日期",index = 1)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value = "收入",index = 2)
    @ColumnWidth(20)
    private double income;

    @ExcelProperty(value = "支出",index = 3)
    @ColumnWidth(20)
    private double disburse;

    @ExcelProperty(value = "方式",index = 4)
    @ColumnWidth(20)
    private String way;

    @ExcelProperty(value = "往来名称",index = 5)
    @ColumnWidth(20)
    private String current_name;

    @ExcelProperty(value = "订单号",index = 6)
    @ColumnWidth(20)
    private String order;

    @ExcelProperty(value = "物品",index = 7)
    @ColumnWidth(20)
    private String good;

    @ExcelProperty(value = "发票状态",index = 8)
    @ColumnWidth(20)
    private String state;

    @ExcelProperty(value = "发票类型",index = 9)
    @ColumnWidth(20)
    private String type;

    @ExcelProperty(value = "项目",index = 10)
    @ColumnWidth(20)
    private String item;

    @ExcelProperty("结余")
    @ColumnWidth(20)
    private double balance;

    @ExcelProperty(value = "费用分类",index = 11)
    @ColumnWidth(20)
    private String classification;

    @ExcelProperty(value = "负责人",index = 12)
    @ColumnWidth(20)
    private String person;

    @ExcelProperty(value = "备注",index = 13)
    @ColumnWidth(20)
    private String remark3;
}
