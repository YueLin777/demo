package com.mind.contract.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：StatementQuery
 *
 * @author:l
 * @Date: 2024/7/16
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementQuery extends BaseParam{
    /*
    * 编号
    * */
    private Integer id;

    /*
     * 账户
     * */
    private String account;

    /*
     *收入日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    /*
     * 收入
     * */
    private double income;

    /*
     * 支出
     * */
    private double disburse;

    /*
     *方式
     * */
    private String way;

    /*
     * 往来名称
     * */
    private String current_name;
    private String current_nameFuzzy;

    /*
    * 订单号
    * */
    private String order;

    /*
     * 物品
     * */
    private String good;

    /*
     * 发票状态
     * */
    private String state;

    /*
     * 发票类型
     * */
    private String type;

    /*
     * 项目
     * */
    private String item;
    private String itemFuzzy;

    /*
     * 余额
     * */
    private double balance;

    /*
     * 费用分类
     * */
    private String classification;

    /*
     * 负责人
     * */
    private String person;

    /*
     * 备注3
     * */
    private String remark3;
}
