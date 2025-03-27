package com.mind.contract.service;

import com.mind.contract.dto.UserInfoDto;
import com.mind.contract.entity.pojo.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> login(User user);

    UserInfoDto getUserInfo(String s);
}
