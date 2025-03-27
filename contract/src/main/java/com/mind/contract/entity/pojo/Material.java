package com.mind.contract.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName：Material
 *
 * @author:l
 * @Date: 2024/8/7
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    /*
     * 编号
     * */
    private Integer id;

    /*
     * 采购日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    /*
     * 采购方式
     * */
    private String way;


    /*
     * 供应商
     * */
    private String suppliers;

    /*
     * 物料名称
     * */
    private String name;

    /*
     * 型号
     * */
    private String model;

    /*
     * 数量
     * */
    private Integer number;

    /*
     * 金额
     * */
    private double money;

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

    /*
     * 项目
     * */
    private String project;

    /*
     * 负责人
     * */
    private String person;

    /*
     * 备注
     * */
    private String remark;
}
