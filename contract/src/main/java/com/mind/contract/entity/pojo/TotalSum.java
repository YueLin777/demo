package com.mind.contract.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName：TotalSUm
 *
 * @author:l
 * @Date: 2024/8/23
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalSum {
    /*
    * 合同金额
    * */
    private double totalMoney;

    /*
    *不开票金额
    * */
    private double totalUnbillAmount;

    /*
    * 已开票金额
    * */
    private double totalAmount;

    /*
    *未开票金额
    * */
    private double totalNotAmount;

    /*
    *收款1
    * */
    private double totalCollection1;

    /*
    * 收款2
    * */
    private double totalCollection2;

    /*
    *收款3
    * */
    private double totalCollection3;

    /*
    *收款4
    * */
    private double totalCollection4;

    /*
    *剩余应收款
    * */
    private double totalNotCollection;
}
