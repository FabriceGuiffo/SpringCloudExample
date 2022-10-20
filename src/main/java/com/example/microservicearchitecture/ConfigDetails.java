package com.example.microservicearchitecture;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties("mes-config")
@RefreshScope
@Getter
@Setter
public class ConfigDetails {
    private int compteur;


}
