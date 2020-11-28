package com.wolf.common.util;
import org.apache.commons.lang3.StringUtils;


/**
 * com.ningpai.sensitive.SensitiveUtils
 * 敏感信息工具类
 * @author lipeng
 * @dateTime 2017/12/12 11:27
 */
public class SensitiveUtils {

    /**
     * 电话号码长度最少位数限制
     */
    public static final Integer PHONE_MIN_LENGTH = 4;

    /**
     * 默认手机号码
     */
    public static final String DEFAULT_MOBILE = "00000000000";

    /**
     * 默认电话号码
     */
    public static final String DEFAULT_PHONE = "000-0000000";

    /**
     * 处理手机号码,隐藏中间的4位
     * @param mobile    手机号码
     * @return          返回处理后的手机号码,如:1326****856
     */
    public static String handlerMobile(String mobile) {
        mobile = StringUtils.isNotEmpty(mobile) ? mobile : DEFAULT_MOBILE;
        mobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return mobile;
    }

    /**
     * 处理电话号码,隐藏后4位
     * @param phone 电话号码
     * @return      返回处理后的电话号码,如果0710-331****
     */
    public static String handlerPhone(String phone) {
        phone = StringUtils.isNotEmpty(phone) && phone.length() >= 4 ? phone : DEFAULT_PHONE;
        phone = phone.substring(0, phone.length() - 4);
        phone = phone + "****";
        return phone;
    }


    /**
     * 电话号码隐藏规则（中间位）
     * @param phone
     * @return
     */
    public  static  String  hideMidPhone(String phone){
        phone = StringUtils.isNotEmpty(phone) && phone.length() >= 4 ? phone : DEFAULT_PHONE;
        phone = phone.substring(0,3)+"****"+phone.substring(7);
        return phone;
    }


    /**
     * 隐藏银行卡号，显示前四位和后四位，中间先写八位
     * @param account
     * @return
     */
    public  static  String  hideMidAccount(String account){
        account = StringUtils.isNotEmpty(account) && account.length() >= 4 ? account : DEFAULT_PHONE;
        account = account.substring(0,4)+"********"+account.substring(account.length()-4,account.length());
        return  account;
    }




    public static void main(String[] args) {
        System.out.println(SensitiveUtils.handlerPhone("1234"));
    }
}
