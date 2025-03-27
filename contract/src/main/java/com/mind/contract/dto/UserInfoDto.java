package com.mind.contract.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoDto {


    /**
     * 权限字符
     */
    private List<String> permissions;

}
