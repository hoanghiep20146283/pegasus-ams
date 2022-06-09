package com.pegasus.ams.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    @Autowired
    private CorsConfigProperties corsConfigProperties;

    private final Logger log = LoggerFactory.getLogger(WebCorsConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.debug("Is allow credentials? : {}", corsConfigProperties.isAllowCredentials());
        registry.addMapping("/**")
                .allowedOrigins(corsConfigProperties.getAllowOrigins().toArray(new String[0]))
                .allowedMethods(RequestMethod.POST.name(), RequestMethod.GET.name(), RequestMethod.PUT.name(), RequestMethod.DELETE.name(), RequestMethod.OPTIONS.name())
                .maxAge(corsConfigProperties.getMaxAge())
                .allowCredentials(corsConfigProperties.isAllowCredentials())
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type", "Referer", "X-XSRF-TOKEN", "Accept");
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "app.cors")
    static class CorsConfigProperties {
        private List<String> allowOrigins = new ArrayList<>();
        private int maxAge = 600;
        private boolean allowCredentials = true;
    }
}
