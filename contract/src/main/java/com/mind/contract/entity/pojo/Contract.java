package com.mind.contract.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：Contract
 *
 * @author:l
 * @Date: 2024/7/15
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    /*
    * 编号
    * */
    private Integer id;

    /*
    * 合同号
    * */
    private String contractId;

    /*
    * 公司
    * */
    private String company;

    /*
    * 日期
    * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    /*
    * 供应商
    * */
    private String supplier;

    /*
    * 物料名称
    * */
    private String material;

    /*
    * 合同金额
    * */
    private double contract_amount;

    /*
    * 付款1
    * */
    private double payment1;

    /*
    * 付款2
    * */
    private double payment2;

    /*
     * 付款3
     * */
    private double payment3;

    /*
     * 付款4
     * */
    private double payment4;

    /*
     * 已付款
     * */
    private double paid;

    /*
     * 剩余应付款
     * */
    private double remain_paid;

    /*
    * 对应项目
    * */
    private String corresponding_item;

    /*
    * 负责人
    * */
    private String person_in_charge;

    /*
    * 已收票金额
    * */
    private double ticket_amount_received;

    /*
    * 未收票金额
    * */
    private double ticket_amount_not_collected;

    /*
    * 税率
    * */
    private double tax_rate;

    /*
    * 备注1
    * */
    private String remark1;

    /*
    * 费用分类
    * */
    private String classification;

    /*
    是否存在合同文件
     */
    private int has_file;
}


