package io.dkargo.bcexplorer.collector.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import log.munzi.interceptor.LoggingInterceptor;
import log.munzi.interceptor.config.ApiLogProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LoggingConfig {

    public final ApiLogProperties apiLogProperties;

    @Bean
    public LoggingInterceptor loggingInterceptor() {

        return new LoggingInterceptor(new ObjectMapper(), apiLogProperties);
    }
}
