package com.liang.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lianghaiyang
 * @date 2019/04/01
 */
@Setter
@Getter
@Component
@ConfigurationProperties("app")
public class AppConfig {
    private String browser;
    private Integer timeout;
}
