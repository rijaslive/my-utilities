package com.apps.essentials.essentials.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration {

    public static final String TASK_EXECUTOR = "taskExecutor";
    public static final String TESSERACT_THREAD_EXECUTOR = "tesseractThreadExecutor";


    @Bean(name = AsyncConfiguration.TASK_EXECUTOR)
    public Executor taskExecutor() {
        log.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("TaskThread-");
        executor.initialize();
        return executor;
    }

    @Bean(name = AsyncConfiguration.TESSERACT_THREAD_EXECUTOR)
    public ExecutorService getTesseractExecutor(){
        int processorCount = Runtime.getRuntime().availableProcessors();

        ExecutorService tesseractExecutor = Executors.newFixedThreadPool(processorCount);
        log.info("Created tesseract executor with {} threads", processorCount);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down the executor...");
            tesseractExecutor.shutdown();  // Initiates a graceful shutdown
        }));
        return  tesseractExecutor;
    }
}