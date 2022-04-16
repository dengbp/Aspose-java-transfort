package com.yrnet.transfer.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class SqlInjectUtil {
    /**
     * 防SQL注入
     * @param str
     * @return
     */
    public static boolean isSqlInj(String str){
        if(StringUtils.isEmpty(str)){
            return true;
        }
        str = str.toLowerCase();

        /**正则表达式**/
        String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

        //b 表示 限定单词边界  比如  select 不通过   1select则是可以的
        Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        if (sqlPattern.matcher(str).find()){
            return false;
        }
        return true;
    }
}
