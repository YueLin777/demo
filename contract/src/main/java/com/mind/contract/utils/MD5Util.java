package com.mind.contract.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author ：long
 * @date ：Created in 2024/8/13 0013 11:07
 * @description：MD5加密工具
 */
public class MD5Util {

    /**
     * 加密方法
     * @param str
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    //固定盐
    private static final String salt = "30c722c6acc64306a88dd93a814c9f0a";

    /**
     * 将用户输入的明文密码与固定盐进行拼装后再进行MD5加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 将form表单中的密码转换成数据库中存储的密码
     * @param formPass
     * @param salt 随机盐
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDbPass("123456"));
    }

}
