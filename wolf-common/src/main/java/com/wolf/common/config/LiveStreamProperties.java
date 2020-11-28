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
@ConfigurationProperties(prefix = "live.stream")
@Data
public class LiveStreamProperties {
    private String appName;
    private String secretId;
    private String secretKey;
    private String pushKey;
    private String pushDomain;
    private String pullDomain;
}
