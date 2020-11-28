package com.wolf.common.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by pingchen on 15/12/29.
 */
public final class StringUtil {

    private final static String MONEY_DIMES_FMT = "0.0";

    private final static String MONEY_CENTS_FMT = "0.00";

    private final static String MONEY_INTEGER_FMT = "0";

    private final static String MONEY_LIKE_FMT = "#.##";


    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    //金额验证
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 支付金额验证，支持最大金额99999999.99
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String reg = "^([1-9]\\d{0,7}(\\.\\d{1,2})?|0(\\.\\d{1,2})?)$";
        Pattern pattern = Pattern.compile(reg);
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^1[3456789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static String fmtIntegerMoney(Object obj) {
        return fmt(obj, MONEY_INTEGER_FMT);
    }

    public static String fmtCentsMoney(Object obj) {
        return fmt(obj, MONEY_CENTS_FMT);
    }

    public static String fmtDimesMoney(Object obj) {
        return fmt(obj, MONEY_DIMES_FMT);
    }

    public static String fmtLikeMoney(Object obj) {
        return fmt(obj, MONEY_LIKE_FMT);
    }

    public static String fmt(Object obj, String fmt) {
        return obj == null ? "" : new DecimalFormat(fmt).format(obj);
    }

    public static boolean find(String str, String regex) {
        return Pattern.compile(regex).matcher(str).find();
    }

    /**
     * 获取货品id /pages/item/item?districtId=929&goodsInfoId=12215
     *
     * @param href
     * @return
     */
    public static Long getGoodsInfoIdByDetailUrl(String href) {
        return Long.valueOf(href.substring(href.indexOf("goodsInfoId") + 12, href.length()));
    }

    /**
     * 去除 回车符，换行符，空格和水平制表符
     * 注：\n 回车(\u000a)
     * 	\t 水平制表符(\u0009)
     * 	\s 空格(\u0008)
     * 	\r 换行(\u000d)*/
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static void main(String[] args) {
        System.out.println(getGoodsInfoIdByDetailUrl("/pages/item/item?districtId=929&goodsInfoId=12215"));
    }
}
