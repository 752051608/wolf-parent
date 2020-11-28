package com.wolf.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信配置
 *
 * @Author honglou
 * @Date 2020/4/2 11:28
 */
@Configuration
//@PropertySource("classpath:xxx.properties")
@ConfigurationProperties(prefix = "aliyunsms")
@Data
public class AliyunSmsProperties {
    public String signName;
    public String templateCode;
    private String accessKeyId;
    private String accessSecret;
    public String pageSize;
}
