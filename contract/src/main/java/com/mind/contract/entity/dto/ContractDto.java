package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ContractDto {
    @ExcelProperty(value="合同号",index = 0)
    @ColumnWidth(20)
    private String contractId;

    @ExcelProperty(value="公司",index = 1)
    @ColumnWidth(20)
    private String company;

    @ExcelProperty(value="日期",index = 2)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value="供应商",index = 3)
    @ColumnWidth(20)
    private String supplier;

    @ExcelProperty(value="物料名称",index = 4)
    @ColumnWidth(20)
    private String material;

    @ExcelProperty(value="合同金额",index = 5)
    @ColumnWidth(20)
    private double contract_amount;

    @ExcelProperty(value="付款1",index = 6)
    @ColumnWidth(20)
    private double payment1;

    @ExcelProperty(value="付款2",index = 7)
    @ColumnWidth(20)
    private double payment2;

    @ExcelProperty(value="付款3",index = 8)
    @ColumnWidth(20)
    private double payment3;

    @ExcelProperty(value="付款4",index = 9)
    @ColumnWidth(20)
    private double payment4;

    @ExcelProperty(value="已付款")
    @ColumnWidth(20)
    private double paid;

    @ExcelProperty(value="剩余应付款")
    @ColumnWidth(20)
    private double remain_paid;

    @ExcelProperty(value="对应项目",index = 10)
    @ColumnWidth(20)
    private String corresponding_item;

    @ExcelProperty(value="负责人",index = 11)
    @ColumnWidth(20)
    private String person_in_charge;

    @ExcelProperty(value="已收票金额")
    @ColumnWidth(20)
    private double ticket_amount_received;

    @ExcelProperty(value="未收票金额")
    @ColumnWidth(20)
    private double ticket_amount_not_collected;

    @ExcelProperty(value="税率",index = 12)
    @ColumnWidth(20)
    private double tax_rate;

    @ExcelProperty(value="费用分类",index = 13)
    @ColumnWidth(20)
    private String classification;

    @ExcelProperty(value="备注",index = 14)
    @ColumnWidth(20)
    private String remark1;
}
