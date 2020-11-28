package com.wolf.config.database;

import com.alibaba.druid.filter.config.ConfigTools;
import org.springframework.stereotype.Service;


/**
 * @author zhanghong
 */
@Service
public class DataSourcePasswordUtils {

    /**
     * 加密
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String encrypt(String cipherText) throws Exception {
        String encrypt = ConfigTools.encrypt(cipherText);
        return encrypt;
    }

    /**
     * 解密
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(String publicKey,String cipherText) throws Exception {
        String decrypt = ConfigTools.decrypt(publicKey,cipherText);
        return decrypt;
    }

    public static void main(String[] args) throws Exception {
        String password = "fDuhqW&cQ##Q87!gpzTgMcFmAD@J2b#k";
        String [] keyPair;
        keyPair = ConfigTools.genKeyPair(512);
        // 私钥
        String privateKey = keyPair[0];
        // 公钥
        String publicKey = keyPair[1];
        String encrypt = ConfigTools.encrypt(privateKey,password);
        System.out.println(encrypt);
        String decrypt = ConfigTools.decrypt(publicKey,encrypt);
        System.out.println(decrypt);

        String passwordDis = AesUtils.decrypt(encrypt, privateKey);
        System.out.println(passwordDis);


    }

}
