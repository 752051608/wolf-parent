package com.wolf.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhanghong
 */
@Configuration
public class DruidDataSourceConfig {

    @Value("${spring.datasource.public.key}")
    public String publicKey;

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSourceConfig() {
        try (DruidDataSource druidDataSource = new UmspscDataSource(publicKey)) {
            return druidDataSource;
        }
    }

}
