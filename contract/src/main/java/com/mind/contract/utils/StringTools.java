package com.mind.contract.utils;

/**
 * ClassName：StringTools
 *
 * @author:l
 * @Date: 2024/7/15
 * @Description:
 * @version: 1.0
 */
public class StringTools {
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str) || "\u0000".equals(str)) {
            return true;
        }else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }
}
