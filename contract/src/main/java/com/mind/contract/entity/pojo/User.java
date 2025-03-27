package com.mind.contract.entity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：long
 * @date ：Created in 2024/8/14 0014 15:41
 * @description：用户类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private int role;
}
