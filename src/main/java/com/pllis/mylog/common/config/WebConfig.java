package com.pllis.mylog.common.config;
import com.pllis.mylog.common.handler.InterceptorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${allow.origins}")
    private final List<String> allowOrigins;

    private final InterceptorHandler interceptorHandler;

    public WebConfig(List<String> allowOrigins, InterceptorHandler interceptorHandler) {
        this.allowOrigins = allowOrigins;
        this.interceptorHandler = interceptorHandler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorHandler)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOrigins(String.join(",", allowOrigins))
            .allowCredentials(true);
    }
}