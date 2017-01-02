package com.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {
    public static String BASE_URI;
    public static String DEFAULT_EMAIL;


    public ApplicationConfiguration(
            @Value("${config.application.base.uri}") String baseUri,
            @Value("${config.application.default.email}") String defaultEmail) {
        ApplicationConfiguration.BASE_URI = baseUri;
        ApplicationConfiguration.DEFAULT_EMAIL = defaultEmail;
    }
}
