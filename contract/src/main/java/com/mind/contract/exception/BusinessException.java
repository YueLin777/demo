package com.mind.contract.exception;

import com.mind.contract.entity.enums.ResponseCodeEnum;

/**
 * ClassName：BusinessException
 *
 * @author:l
 * @Date: 2024/7/15
 * @Description:
 * @version: 1.0
 */
public class BusinessException extends RuntimeException{
    private ResponseCodeEnum codeEnum;
    private Integer code;
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public ResponseCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(ResponseCodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
