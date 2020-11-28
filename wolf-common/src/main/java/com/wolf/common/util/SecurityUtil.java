package com.wolf.common.util;

import com.wolf.common.util.uuid.UUIDUtil;

import java.math.BigInteger;
import java.util.Random;

/**
 * 密码安全帮助类
 *
 * @author lihe 2013-7-4 下午5:30:05
 * @see
 */
public final class SecurityUtil {

    private static final BigInteger PRIVATE_D = new BigInteger(
            "3206586642942415709865087389521403230384599658161226562177807849299468150139");

    private static final BigInteger N = new BigInteger(
            "7318321375709168120463791861978437703461807315898125152257493378072925281977");

    private SecurityUtil() {

    }

    /**
     * 生成加密后的密码--密文
     * 用户 密码校验 或者 修改密码
     * @param userCode 用户id
     * @param loginPwd  登陆密码
     * @param saltCode 加盐字符串--注册时调用getNewPsw
     * @return
     * @author lihe 2012-9-27 上午9:58:10
     * @see
     */
    public static String getStoreLogpwd(String userCode, String loginPwd, String saltCode) {
        return MD5Util.md5Hex(userCode + MD5Util.md5Hex(loginPwd) + saltCode);
    }

    /**
     * 生成盐值--密码安全码
     *
     * @return
     * @author lihe 2012-9-27 上午9:40:51
     * @see
     */
    public static String getSaltCode() {
        String s1 = MD5Util.md5Hex(String.valueOf(System.currentTimeMillis()));
        String s2 = UUIDUtil.getUUID();
        return s1 + s2;
    }


    /**
     * 解析前台传送的加密字符
     *
     * @param str
     * @return
     * @author lihe 2012-9-26 上午10:51:28
     * @see
     */
    public static String getDecryptLoginPassword(String str) {
        byte ptext[] = HexUtil.toByteArray(str);
        BigInteger encryC = new BigInteger(ptext);

        BigInteger variable = encryC.modPow(PRIVATE_D, N);
        // 计算明文对应的字符串
        byte[] mt = variable.toByteArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = mt.length - 1; i > -1; i--) {
            buffer.append((char) mt[i]);
        }
        return buffer.substring(0, buffer.length() - 10);
    }


    /**
     * 生成随机的MD5密文
     *
     * @return
     */
    public static String getNewToken() {
        return MD5Util.md5Hex(java.util.UUID.randomUUID().toString());
    }

    /**
     * 生成随机正整数
     *
     * @param maxInt
     * @return
     */
    public static int getRandomInt(int maxInt) {
        Random random = new Random();
        return random.nextInt(maxInt);
    }

    /**
     * 生成签名
     *
     * @param srcStr
     * @return
     */
    public static String getDigest(String srcStr) {
        return MD5Util.md5Hex(MD5Util.md5Hex(srcStr) + (srcStr.hashCode() + srcStr.length()) + "19880322");
    }

    /**
     * 生成指定长度的验证码
     * @param len
     * @return
     */
    public static String getVerifyCode(int len){
        StringBuilder sb = new StringBuilder(len );
        for (int i=0;i<len;i++){
            sb.append(getRandomInt(10));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //手机号 15088543453
        System.out.println(getStoreLogpwd(String.valueOf(49),"123456","cfc27419e99dca0ff678176d01018c637ffffe8b0dd3224e0d140f138347f48f"));
    }


}
