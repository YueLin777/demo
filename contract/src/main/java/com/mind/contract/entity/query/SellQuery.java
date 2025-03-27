package com.mind.contract.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.vo.PaginationResultVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：SellQuery
 *
 * @author:l
 * @Date: 2024/8/2
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellQuery extends BaseParam{
    /*
     * 编号
     * */
    private Integer id;

    /*
     * 合同号
     * */
    private String contractId;
    private String contractIdFuzzy;

    private String residualCollectionFuzzy;

    /*
     * 日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    /*
     * 公司
     * */
    private String company;


    /*
     * 有无纸质双方盖章
     * */
    private String seal;

    /*
     * 档案编号
     * */
    private String file;

    /*
     * 负责人
     * */
    private String person;

    /*
     * 客户
     * */
    private String customer;

    /*
     * 合同金额
     * */
    private double money;

    /*
     * 开票状态
     * */
    private String billingStatus;

    /*
     * 交付状态
     * */
    private String deliveryStatus;

    /*
     * 已开票金额
     * */
    private double invoicedAmount;

    /*
     * 未开票金额
     * */
    private double invoiceNotAmount;

    /*
     * 产品名称
     * */
    private String product;

    /*
     * 合同详情
     * */
    private String detail;

    /*
     * 收款1
     * */
    private double collection1;

    /*
     * 收款2
     * */
    private double collection2;

    /*
     * 收款3
     * */
    private double collection3;

    /*
     * 收款4
     * */
    private double collection4;

    /*
     * 已付款
     * */
    private double receivedCollection;

    /*
     * 剩余应付款
     * */
    private double residualCollection;

    /*
     * 状态
     * */
    private String state;

    /*
     * 备注
     * */
    private String remark;

}
