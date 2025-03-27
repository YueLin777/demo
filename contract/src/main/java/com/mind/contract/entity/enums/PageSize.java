package com.mind.contract.entity.enums;

/**
 * ClassName：PageSize
 *
 * @author:l
 * @Date: 2024/7/16
 * @Description:
 * @version: 1.0
 */
public enum PageSize {
    SIZE5(5),SIZE10(10),SIZE15(15),SIZE20(20),SIZE25(25),
    SIZE30(30),SIZE35(35),SIZE40(40),SIZE45(45),SIZE50(50),;

    int size;

    PageSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
