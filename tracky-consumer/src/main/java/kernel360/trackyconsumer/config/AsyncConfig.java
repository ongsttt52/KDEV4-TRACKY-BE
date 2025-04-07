package kernel360.trackyconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

	// 최대 4개의 스레드, 20개의 작업 대기 가능
	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() + 1);	// 기본 유지 스레드
		executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);	// 최대 스레드 수
		// executor.setCorePoolSize(2);	// 기본 유지 스레드
        // executor.setMaxPoolSize(4);		// 최대 스레드 수
		executor.setQueueCapacity(20);	// 큐 용량(작업 수)
		executor.setThreadNamePrefix("AsyncThread-"); // 생성되는 스레드 접두사
		executor.initialize();
		return executor;
	}
}
