package com.lan.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来转换VO和PO集合
 * @author lanjian
 * */
public class BeanUtil {

    public static <T> T copy(Object poObj,final Class <T>voClass)
    {
        T voObj;
        try {
            voObj = voClass.newInstance();
            BeanUtils.copyProperties(poObj, voObj);
            return voObj;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> List <T> copyList(List <? extends Object> poList ,final Class <T>voClass){

        List<T> voList= new ArrayList<>();

        T voObj;
        for(Object poObj:poList){
            try {
                voObj = voClass.newInstance();
                BeanUtils.copyProperties(poObj, voObj);
                voList.add(voObj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            /*System.out.println(voObj);*/
        }
        return voList;
    }
}
