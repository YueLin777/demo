package com.mind.contract.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName：accountSum
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSum {
    private String account;
    private double totalBalance;
}
