package com.mind.contract.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ClassName：InvoiceQuery
 *
 * @author:l
 * @Date: 2024/7/16
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceQuery extends BaseParam{
    /*
     * 发票所属合同订单
     * */
    private String invoice_belongs_to_the_contract_order;
    private String invoice_belongs_to_the_contract_orderFuzzy;
    /*
     *开票日期
     * */
    private Date date_of_drawing;

    /*
     * 收到日期
     * */
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
    private String invoice_codeFuzzy;

    /*
     * 发票号码
     * */
    private String invoice_number;
    private String invoice_numberFuzzy;

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
}
