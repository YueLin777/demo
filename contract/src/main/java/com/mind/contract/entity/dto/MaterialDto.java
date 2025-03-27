package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName：Material
 *
 * @author:l
 * @Date: 2024/8/7
 * @Description:
 * @version: 1.0
 */
@Data
public class MaterialDto {
    @ExcelProperty("编号")
    @ColumnWidth(20)
    private Integer id;

    @ExcelProperty(value = "采购日期",index = 0)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value = "采购方式",index = 1)
    @ColumnWidth(20)
    private String way;

    @ExcelProperty(value = "供应商",index = 2)
    @ColumnWidth(20)
    private String suppliers;

    @ExcelProperty(value = "物料名称",index = 3)
    @ColumnWidth(20)
    private String name;

    @ExcelProperty(value = "型号",index = 4)
    @ColumnWidth(20)
    private String model;

    @ExcelProperty(value = "数量",index = 5)
    @ColumnWidth(20)
    private Integer number;

    @ExcelProperty(value = "金额",index = 6)
    @ColumnWidth(20)
    private double money;

    @ExcelProperty(value = "税率",index = 7)
    @ColumnWidth(20)
    private double taxRate;

    @ExcelProperty("税额")
    @ColumnWidth(20)
    private double taxAmount;

    @ExcelProperty("不含税金额")
    @ColumnWidth(20)
    private double taxNotAmount;

    @ExcelProperty(value = "项目",index = 8)
    @ColumnWidth(20)
    private String project;

    @ExcelProperty(value = "负责人",index = 9)
    @ColumnWidth(20)
    private String person;

    @ExcelProperty(value = "备注",index = 10)
    @ColumnWidth(20)
    private String remark;
}
