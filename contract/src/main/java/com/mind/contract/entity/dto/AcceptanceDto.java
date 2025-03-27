package com.mind.contract.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName：AcceptanceDto
 *
 * @author:l
 * @Date: 2024/8/23
 * @Description:
 * @version: 1.0
 */
@Data
public class AcceptanceDto {
    @ExcelProperty(value = "日期",index = 0)
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(20)
    private Date date;

    @ExcelProperty(value = "客户",index = 1)
    @ColumnWidth(20)
    private String customer;

    @ExcelProperty(value = "产品名称",index = 2)
    @ColumnWidth(20)
    private String name;

    @ExcelProperty(value = "文件类型",index = 3)
    @ColumnWidth(20)
    private String type;
}
