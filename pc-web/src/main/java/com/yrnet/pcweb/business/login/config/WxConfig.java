package com.yrnet.pcweb.business.login.config;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/12/1 6:26 下午
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:/wxconfig.properties"})
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    private String appId;
    private String secret;
}
