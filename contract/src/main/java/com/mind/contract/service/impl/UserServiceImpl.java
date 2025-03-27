package com.mind.contract.service.impl;

import com.mind.contract.dao.UserDao;
import com.mind.contract.dto.UserInfoDto;
import com.mind.contract.entity.pojo.User;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.UserService;
import com.mind.contract.utils.MD5Util;
import com.mind.contract.utils.RSAUtil;
import com.mind.contract.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：long
 * @date ：Created in 2024/8/14 0014 17:09
 * @description：
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Map<String, Object> login(User user) {
        Map<String,Object> result=new HashMap<>();
        User userByUsername = userDao.queryUserByUsername(user.getUsername());
        if (userByUsername == null) {
            throw new BusinessException("用户不存在");
        }
        try {
            String password= RSAUtil.decryptByPriKey(user.getPassword(), RSAUtil.PRIVATE_KEY);
            if (!password.equals(userByUsername.getPassword())) {
                throw new BusinessException("密码错误");
            }
            String sign = TokenUtils.sign(userByUsername);
            result.put("token",sign);
            userByUsername.setPassword(MD5Util.inputPassToDbPass(userByUsername.getPassword()));
            result.put("user",userByUsername);
        } catch (Exception e) {
            throw new BusinessException("数据异常");
        }
        return result;
    }

    @Override
    public UserInfoDto getUserInfo(String id) {

        UserInfoDto userInfoDto = new UserInfoDto();

        //设置权限字符
        List<String> permissions = userDao.getUserPermissions(id);
        userInfoDto.setPermissions(permissions);

        return userInfoDto;
    }


}



