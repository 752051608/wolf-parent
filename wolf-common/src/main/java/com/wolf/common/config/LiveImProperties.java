package com.wolf.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云直播相关对接参数
 *
 * @author hong
 * @date 2020-04-23 14:45
 */
@Configuration
@ConfigurationProperties(prefix = "live.im")
@Data
public class LiveImProperties {
    public Long sdkAppId;
    private String sdkSecret;
    private String accountType;
    public String administrator;
}
