package com.lan.util;

public interface LJException {

    /**
     * 异常码
     * @return  返回异常码
     */
    int getCode();

    /**
     * 异常描述
     * @return  返回异常描述
     */
    String getDescription();
}
