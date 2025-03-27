package com.mind.contract.controller;

import com.mind.contract.dto.UserInfoDto;
import com.mind.contract.entity.pojo.User;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.UserService;
import com.mind.contract.utils.TokenUtils;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author ：long
 * @date ：Created in 2024/8/13 0013 14:55
 * @description：用户
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public ResponseVo login(@RequestBody User user){
        if (StringUtils.isNullOrEmpty(user.getUsername()) &&
                StringUtils.isNullOrEmpty(user.getPassword())){
            throw new BusinessException("用户名或者密码不能为空");
        }
        Map<String,Object> result=userService.login(user);
        return ResponseVo.ok(result);
    }

    @RequestMapping("/getUserInfo")
    public ResponseVo<UserInfoDto> getUserInfo(HttpServletRequest request){

        String token = request.getHeader("Token");
        if(ObjectUtils.isEmpty(token)){
            throw new BusinessException("请先登录");
        }
        String userId = TokenUtils.getUserId(token);

        return ResponseVo.ok(userService.getUserInfo(userId));
    }


}
