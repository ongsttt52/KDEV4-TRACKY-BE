package kernel360.trackyconsumer.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {

    @Bean(name = "taskExecutor")ㅇ
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 10);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 20);
        executor.setQueueCapacity(20000);
        executor.setThreadNamePrefix("AsyncThread-");
        
        // 설정 정보 로깅
        log.info("Available processors: {}", Runtime.getRuntime().availableProcessors());
        log.info("Core pool size: {}", executor.getCorePoolSize());
        log.info("Max pool size: {}", executor.getMaxPoolSize());
        log.info("Queue capacity: {}", executor.getQueueCapacity());
        
        executor.initialize();
        return executor;
    }
}
