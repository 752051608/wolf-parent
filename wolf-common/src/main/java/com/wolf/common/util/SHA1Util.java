package com.wolf.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * SHA1 计算工具类
 */
public class SHA1Util {
    private static final Log logger = LogFactory.getLog(SHA1Util.class);

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000L);
    }

    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();

        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            sb.append(k + "=" + v + "&");
        }

        String params = sb.substring(0, sb.lastIndexOf("&"));
        return getSha1(params);
    }

    public static String getSha1(String str) {
        if (str != null && str.length() != 0) {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            try {
                MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
                mdTemp.update(str.getBytes("UTF-8"));
                byte[] md = mdTemp.digest();
                int j = md.length;
                char[] buf = new char[j * 2];
                int k = 0;

                for(int i = 0; i < j; ++i) {
                    byte byte0 = md[i];
                    buf[k++] = hexDigits[byte0 >>> 4 & 15];
                    buf[k++] = hexDigits[byte0 & 15];
                }

                return new String(buf);
            } catch (Exception var9) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * SHA1 加密
     *
     * @param s 待加密的字符串
     * @return
     */
    public final static String Sha1(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("sha-1");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("Sha1 error", e);
            throw e;
        }
    }
}
