package com.mind.contract.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSum {
    /*对应项目*/
    private String item;
    /*对应总的合同金额*/
    private double totalAmount;
    /*对应总的支出*/
    private double totalDisburse;
}
