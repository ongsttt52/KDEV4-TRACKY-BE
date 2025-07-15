package kernel360.trackyconsumer.common.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConfig {

	private final RabbitMQProperties properties;
	private final MeterRegistry meterRegistry;

	// yml 파일에서 큐 이름을 주입받습니다.
	@Value("${rabbitmq.queue.gps}")
	private String gpsQueueName;

	@Value("${rabbitmq.queue.onoff}")
	private String onoffQueueName;

	@Value("${rabbitmq.queue.web}")
	private String webQueueName;

	@Value("${rabbitmq.queue.dlq}")
	private String deadLetterQueueName;

	/**
	 * gps-queue 라는 이름의 큐 Bean을 생성합니다.
	 * durable: true 속성으로 RabbitMQ 서버가 재시작되어도 큐가 유지되도록 합니다.
	 */
	@Bean
	public Queue gpsQueue() {
		return new Queue(gpsQueueName, true);
	}

	@Bean
	public Queue onoffQueue() {
		return new Queue(onoffQueueName, true);
	}

	@Bean
	public Queue webQueue() {
		return new Queue(webQueueName, true);
	}

	@Bean
	public Queue deadLetterQueue() {
		return new Queue(deadLetterQueueName, true);
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory batchRabbitListenerContainerFactory(
		ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setBatchSize(properties.getBatch().getSize());
		factory.setReceiveTimeout(properties.getBatch().getTimeout());
		factory.setBatchListener(properties.getBatch().isEnabled());
		factory.setConsumerBatchEnabled(properties.getBatch().isConsumerBatchEnabled());
		factory.setMessageConverter(messageConverter());

		// 리스너 스레드 설정 (배치 리스너에서도 10개의 컨슈머 사용)
		factory.setConcurrentConsumers(10);
		factory.setMaxConcurrentConsumers(10);  // 최대 컨슈머도 동일하게 설정

		// 메트릭 측정을 위한 설정
		factory.setMicrometerEnabled(true);  // Micrometer 메트릭 활성화
		factory.setObservationEnabled(true); // 관찰 활성화 (Spring Boot 3.0 이상)

		// 오류 핸들링 설정
		factory.setErrorHandler(throwable -> {
			log.error("RabbitMQ 메시지 처리 중 오류 발생: {}", throwable.getMessage(), throwable);
		});

		factory.setDefaultRequeueRejected(properties.getBatch().isDefaultRequeueRejected());

		log.info(
			"RabbitMQ 배치 리스너 설정: concurrentConsumers={}, batchSize={}, timeout={}, enabled={}, consumerBatchEnabled={}",
			10, properties.getBatch().getSize(), properties.getBatch().getTimeout(),
			properties.getBatch().isEnabled(), properties.getBatch().isConsumerBatchEnabled());

		return factory;
	}

	// 일반 리스너 컨테이너 팩토리도 정의하여 메트릭 수집 보장
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
		ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(messageConverter());
		factory.setConcurrentConsumers(5);
		factory.setMaxConcurrentConsumers(10);

		factory.setMicrometerEnabled(true);
		factory.setObservationEnabled(true);

		log.info("RabbitMQ 일반 리스너 설정: concurrentConsumers=5, maxConcurrentConsumers=10");

		return factory;
	}
}
