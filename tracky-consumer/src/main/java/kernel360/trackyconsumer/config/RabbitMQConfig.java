package kernel360.trackyconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RabbitMQConfig {

	public static final String EXCHANGE_NAME = "car-info-exchange";
	public static final String GPS_QUEUE_NAME = "gps-queue";
	public static final String ONOFF_QUEUE_NAME = "on-off-Queue";
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	public DirectExchange deadLetterExchange() {
		return new DirectExchange("dlx");
	}
	
	@Bean
	public Queue gpsQueue() {
		return QueueBuilder.durable(GPS_QUEUE_NAME)
            .withArgument("x-dead-letter-exchange", "dlx")  // DL 교환기 이름
            .withArgument("x-dead-letter-routing-key", "deadLetter")  // DL 라우팅 키
            .build();
	}
	
	@Bean
	public Queue onOffQueue() {
		return new Queue(ONOFF_QUEUE_NAME, true);
	}

	@Bean
	public Queue deadLetterQueue() {
		return new Queue("dead-letter-queue");
	}
	
	@Bean
	public Binding gpsBinding(Queue gpsQueue, TopicExchange exchange) {
		return BindingBuilder.bind(gpsQueue).to(exchange).with("gps");
	}
	
	@Bean
	public Binding onBinding(Queue onOffQueue, TopicExchange exchange) {
		return BindingBuilder.bind(onOffQueue).to(exchange).with("on");
	}
	
	@Bean
	public Binding offBinding(Queue onOffQueue, TopicExchange exchange) {
		return BindingBuilder.bind(onOffQueue).to(exchange).with("off");
	}

	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue())
				.to(deadLetterExchange()).with("deadLetter");
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// 배치 처리를 위한 컨테이너 팩토리 추가
	@Bean
	public SimpleRabbitListenerContainerFactory batchRabbitListenerContainerFactory(
			ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setBatchSize(10); // 10개의 메시지를 한 번에 처리
		factory.setReceiveTimeout(10000L); // 10초 대기 후 타임아웃
		factory.setBatchListener(true); // 배치 리스너 활성화(단일 메시지가 아닌 메시지 리스트 처리하도록록)
		factory.setConsumerBatchEnabled(true); // Spring AMQP의 내부 배치 처리 기능을 활성화
		factory.setMessageConverter(messageConverter());

		// 에러 핸들러 - 예외 발생 시 로깅 및 알림 처리
		factory.setErrorHandler(throwable -> {
			log.error("배치 처리 중 오류 발생: {}", throwable.getMessage());
		});
		
		// true: 예외 발생 시 재큐잉O / false: 재큐잉X
		factory.setDefaultRequeueRejected(false);
		
		return factory;
	}

}