package com.mykaneki.generatenotify.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ChatGptApi {
    private String url;
    private String apiKey;
}
