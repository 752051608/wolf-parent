package com.wolf.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情符号
 * @author zhanghong
 */
public class CharUtils {

    public static String emojiFilter(String str) {

        if(str==null || str.trim().isEmpty()){
            return str;
        }
        String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr="";
        Pattern emoji=Pattern.compile(pattern);
        Matcher emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;
    }

}
