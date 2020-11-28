package com.wolf.config.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * aes加解密工具
 *
 * @author honglou
 * @date 2019-12-13 11:56
 */
@Slf4j
public class AesUtils {
    private static final String CHAR_SET = "UTF-8";

    /**
     * 加密
     *
     * @param contentParam 需要加密的内容
     * @param keyParam     加密密码
     * @param md5Key       是否对key进行md5加密
     * @param ivParam      加密向量
     * @return 加密后的字节数据 string
     */
    public static String encrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
        try {
            byte[] content = contentParam.getBytes(CHAR_SET);
            byte[] key = keyParam.getBytes(CHAR_SET);
            byte[] iv = null;
            if (StringUtils.isNotBlank(ivParam)) {
                iv = ivParam.getBytes(CHAR_SET);
            }
            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            //使用CBC模式, 需要一个向量iv, 可增加加密算法的强度
            IvParameterSpec ivps = null;
            if (iv != null) {
                ivps = new IvParameterSpec(iv, 0, 16);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            } else {
                ivps = new IvParameterSpec(key, 0, 16);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            }
            byte[] bytes = cipher.doFinal(content);
            return new BASE64Encoder().encode(bytes);
        } catch (Exception ex) {
            log.error("加密密码失败", ex);
        }
        return "";
    }

    /**
     * 加密
     *
     * @param contentParam 需要加密的内容
     * @param keyParam     加密密码
     * @return 加密后的字节数据 string
     */
    public static String encrypt(String contentParam, String keyParam) {
        return encrypt(contentParam, keyParam, true, null);
    }

    /**
     * 解密
     *
     * @param contentParam 需要加密的内容
     * @param keyParam     加密密码
     * @return string
     */
    public static String decrypt(String contentParam, String keyParam) {
        return decrypt(contentParam, keyParam, true, null);
    }

    /**
     * 解密
     *
     * @param contentParam 需要加密的内容
     * @param keyParam     加密密码
     * @param md5Key       是否对key进行md5加密
     * @param ivParam      加密向量
     * @return string
     */
    public static String decrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
        try {
            //如果有一个为空，则返回true
//            if (StringUtils.isBlank(contentParam) || StringUtils.isBlank(keyParam) || StringUtils.isBlank(ivParam)) {
//                return "";
//            }
            byte[] content = new BASE64Decoder().decodeBuffer(contentParam);
            byte[] key = keyParam.getBytes(CHAR_SET);
            byte[] iv = null;
            if (StringUtils.isNotBlank(ivParam)) {
                iv = ivParam.getBytes(CHAR_SET);
            }

            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            //使用CBC模式, 需要一个向量iv, 可增加加密算法的强度
            IvParameterSpec ivps = null;
            if (iv != null) {
                ivps = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            } else {
                ivps = new IvParameterSpec(key, 0, 16);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            }
            byte[] bytes = cipher.doFinal(content);
            return new String(bytes, CHAR_SET);
        } catch (Exception ex) {
            log.error("解密密码失败", ex);
        }
        return "";
    }

//    public static void main(String[] args) throws Exception {
//        String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJfswNFm2m+MJ77ezkaj11aNGjJD3L9IZuAf6kqTyvqshPqG4vE4NjTVltB++j764gENPVlJxdWxOewky6f8gB8CAwEAAQ==";
////        String afterText = encrypt("fDuhqW&cQ##Q87!gpzTgMcFmAD@J2b#k", key);
//        String afterText = encrypt("fDuhqW&cQ##Q87!gpzTgMcFmAD@J2b#k", key);
//        String beforeText = decrypt(afterText, key);
//        System.out.println("加密后的信息" + afterText);
//        System.out.println("解密后的信息" + beforeText);
//    }
}
