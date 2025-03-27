package com.mind.contract.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ClassName：StatementSummary
 *
 * @author:l
 * @Date: 2024/7/26
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementSummary {
    /*
     * 总收入
     * */
    private double totalIncome;

    /*
     * 总支出
     * */
    private double totalDisburse;

    /*
     * 结余
     * */
    private double totalBalance;


    // 添加时间范围字段
    private LocalDate startDate;
    private LocalDate endDate;
}
