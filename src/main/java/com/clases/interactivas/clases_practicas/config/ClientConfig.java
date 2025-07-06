package com.clases.interactivas.clases_practicas.config;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@Configuration
public class ClientConfig {

    // Considerar externalizar estas propiedades a application.properties
    @Value("${executor.corePoolSize:5}")
    private int corePoolSize;

    @Value("${executor.maxPoolSize:10}")
    private int maxPoolSize;

    @Value("${executor.queueCapacity:100}")
    private int queueCapacity;

    @Value("${executor.threadNamePrefix:Async-Executor-}")
    private String threadNamePrefix;

    @Bean
    public RestTemplate restTemplateConfigurer() {
        return new RestTemplate();
    }

    @Bean // Considerar renombrar a asyncTaskExecutor o similar
    public Executor taskExecutor() { // Renombrado de myThreadPoolTaskExecutor a taskExecutor
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
