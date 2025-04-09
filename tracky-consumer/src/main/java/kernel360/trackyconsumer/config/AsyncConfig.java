package kernel360.trackyconsumer.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

	// 최대 4개의 스레드, 20개의 작업 대기 가능
	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 10);    // 기본 유지 스레드
		executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 20);    // 최대 스레드 수
		// executor.setCorePoolSize(4);	// 기본 유지 스레드
		// executor.setMaxPoolSize(8);		// 최대 스레드 수
		executor.setQueueCapacity(20000);    // 큐 용량(작업 수)
		executor.setThreadNamePrefix("AsyncThread-"); // 생성되는 스레드 접두사
		executor.initialize();
		return executor;
	}
}
