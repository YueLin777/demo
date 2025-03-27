package com.mind.contract.controller;

import com.mind.contract.entity.enums.ResponseCodeEnum;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName：GlobalExceptionHandleController
 *
 * @author:l
 * @Date: 2024/7/15
 * @Description:
 * @version: 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandleController {
    private static final String ERROR = "error";

    //全局异常处理
    @ExceptionHandler(value = Exception.class)
    Object handException(Exception e, HttpServletRequest request) {
        log.error("请求错误，请求地址:{}，错误信息：",request.getRequestURL(), e);

        ResponseVo responseVo = new ResponseVo();
        //处理：
        //页面不存在 404
        if (e instanceof NoHandlerFoundException) {
            responseVo.setCode(ResponseCodeEnum.CODE_404.getCode());
            responseVo.setMessage(ResponseCodeEnum.CODE_404.getMsg());
            responseVo.setStatus(ERROR);
        }else if (e instanceof BusinessException){
            //业务异常
            responseVo.setCode(ResponseCodeEnum.CODE_600.getCode());
            responseVo.setMessage(e.getMessage());
            responseVo.setStatus(ERROR);
        }else {//其他异常
            responseVo.setCode(ResponseCodeEnum.CODE_500.getCode());
            responseVo.setMessage(ResponseCodeEnum.CODE_500.getMsg());
            responseVo.setStatus(ERROR);
        }

        return responseVo;
    }
}
