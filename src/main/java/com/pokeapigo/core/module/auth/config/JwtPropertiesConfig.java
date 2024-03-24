package com.pokeapigo.core.module.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtPropertiesConfig {
    private String secretKey;
    private Long expireTimeHours;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getExpireTimeHours() {
        return expireTimeHours;
    }

    public void setExpireTimeHours(Long expireTimeHours) {
        this.expireTimeHours = expireTimeHours;
    }
}
