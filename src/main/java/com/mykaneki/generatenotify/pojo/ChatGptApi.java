package com.mykaneki.generatenotify.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Data
@Component
// @ConfigurationProperties(prefix = "api")
public class ChatGptApi {
    private String url;
    private String apiKey;

    @Autowired
    public ChatGptApi(Environment environment) {
        this.url = environment.getProperty("url");
        this.apiKey = environment.getProperty("apiKey");
    }
}
