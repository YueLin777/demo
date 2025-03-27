package com.mind.contract.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：Invoice
 *
 * @author:l
 * @Date: 2024/7/15
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    /*
     * 发票所属合同订单
     * */
    private String invoice_belongs_to_the_contract_order;
    /*
     *开票日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date_of_drawing;

    /*
     * 收到日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date_received;

    /*
     * 发票抬头
     * */
    private String invoice_title;

    /*
     * 发票类型
     * */
    private String invoice_type;

    /*
     * 发票代码
     * */
    private String invoice_code;

    /*
     * 发票号码
     * */
    private String invoice_number;

    /*
     * 开票公司名称
     * */
    private String billing_company;

    /*
     * 内容
     * */
    private String content;

    /*
     * 总金额
     * */
    private double total_amount;

    /*
     * 税率
     * */
    private double tax_rate;

    /*
     * 不含税金额
     * */
    private double tax_not_included;

    /*
     * 税额
     * */
    private double amount_of_tax_payable;

    /*
     * 开票来源
     * */
    private String billing_source;

    /*
     * 交会计年份
     * */
    private int accounting_year;

    /*
     * 交会计月份
     * */
    private int accounting_month;

    /*
     * 备注2
     * */
    private String remark2;

    /*
    是否存在合同文件
    */
    private int has_file;
}
