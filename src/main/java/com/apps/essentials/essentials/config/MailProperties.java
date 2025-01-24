package com.apps.essentials.essentials.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "mail")
@Data
public class MailProperties {
    private List<String> to;
    private List<String> cc;
    private List<String> dummy;
}
