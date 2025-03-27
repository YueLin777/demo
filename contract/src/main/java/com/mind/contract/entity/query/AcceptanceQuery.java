package com.mind.contract.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptanceQuery extends BaseParam{
    /*
     * 编号
     * */
    private Integer id;

    /*
     * 日期
     * */
    //将Data 转成String ，一般后台传值给前台时
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //将String 转成 Data ，一般前台传值给后台时
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;

    /*
     * 客户
     * */
    private String customer;

    /*
     * 产品名称
     * */
    private String name;

    /*
     * 文件类型
     * */
    private String type;

    private String fuzzy;
}
