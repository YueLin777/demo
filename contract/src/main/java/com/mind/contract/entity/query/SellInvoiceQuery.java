package com.mind.contract.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：SellInvoiceQuery
 *
 * @author:l
 * @Date: 2024/8/2
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellInvoiceQuery extends BaseParam{
    /*
     * 编号
     * */
    private Integer id;

    /*
     * 公司
     * */
    private String company;

    /*
     *日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    /*
     * 发票号码
     * */
    private String invoiceNumber;

    /*
     * 客户名称
     * */
    private String customerName;

    /*
     * 纳税人识别号
     * */
    private String ratepayer;

    /*
     * 地址
     * */
    private String position;

    /*
     *公司电话
     * */
    private String phone;

    /*
     * 开户行及账号
     * */
    private String account;

    /*
     * 货物名称
     * */
    private String cargoName;

    /*
     * 对应合同号/订单号
     * */
    private String contractId;
    private String contractIdFuzzy;

    /*
     * 开票金额
     * */
    private double invoiceAmount;

    /*
     * 税率
     * */
    private double taxRate;

    /*
     * 税额
     * */
    private double taxAmount;

    /*
     * 不含税金额
     * */
    private double taxNotAmount;

}
