package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName：DeliveryDto
 *
 * @author:l
 * @Date: 2024/8/23
 * @Description:
 * @version: 1.0
 */
@Data
public class DeliveryDto {
    @ExcelProperty(value = "发货日期",index = 0)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value = "客户名称",index = 1)
    @ColumnWidth(20)
    private String name;

    @ExcelProperty(value = "合同号",index = 2)
    @ColumnWidth(20)
    private Integer contractId;

    @ExcelProperty(value = "货物名称",index = 3)
    @ColumnWidth(20)
    private String good;

}
