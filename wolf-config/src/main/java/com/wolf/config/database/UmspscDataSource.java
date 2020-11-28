package com.wolf.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * 重写druid的加解密过程
 *
 * @author hong
 * @date 2019-12-19 19:09
 */
@Slf4j
public class UmspscDataSource extends DruidDataSource {

    private String publicKey;
    public UmspscDataSource(String publicKey){
        this.publicKey = publicKey;
    }



    @Override
    public String getPassword() {
        String encPassword = super.getPassword();
        if (null == encPassword) {
            return null;
        }
        log.info("数据库密码加解密，{" + encPassword + "}");
        try {
            //使用自定义的加解密方式,AesUtils未能解密ok
//            passwordDis = AesUtils.decrypt(encPassword, publicKey);
            //使用默认的加解密方式
//            passwordDis = DataSourcePasswordUtils.decrypt(encPassword);
            String passwordDis = DataSourcePasswordUtils.decrypt(publicKey,encPassword);
            return passwordDis;
        } catch (Exception e) {
            log.error("数据库密码解密出错，{" + encPassword + "}");
        }
        return null;
    }
}
