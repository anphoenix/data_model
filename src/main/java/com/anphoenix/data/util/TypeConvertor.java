package com.anphoenix.data.util;

import com.anphoenix.data.model.Person;

/**
 * Created by stefanie on 6/5/14.
 */
public class TypeConvertor {
    public static Integer getInt(String str){
        try{
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Boolean getBool(Integer num){
        return num == 0 ? false : true;
    }

    public static Boolean getBool(String str){
        return "0".equals(str) ? false : true;
    }

    public static Person.Gender getGender(String str){
        return "f".equalsIgnoreCase(str) ? Person.Gender.FEMALE : Person.Gender.MALE;
    }
}
