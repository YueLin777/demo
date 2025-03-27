package com.mind.contract.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName：TimeSum
 *
 * @author:l
 * @Date: 2024/8/21
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSum {
    /*年份*/
    private String year;
    /*月份*/
    private String month;
    /*type*/
    private String invoiceType;
    /**/
    private double totalAmount;
    private double amount;
    private double notAmount;
}
