package kernel360trackybe.trackyhub.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

	private final RabbitMQProperties properties;

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(properties.getExchange().getCarInfo());
	}

	@Bean
	public Queue gpsQueue() {
		return QueueBuilder.durable(properties.getQueue().getGps())
			.withArgument("x-dead-letter-exchange", properties.getExchange().getDlx())
			.withArgument("x-dead-letter-routing-key", properties.getRouting().getDeadLetterKey())
			.build();
	}

	@Bean
	public Queue onOffQueue() {
		return new Queue(properties.getQueue().getOnoff(), true);
	}

	@Bean
	public Queue deadLetterQueue() {
		return new Queue(properties.getQueue().getDlq());
	}

	@Bean
	public Binding gpsBinding(Queue gpsQueue, TopicExchange exchange) {
		return BindingBuilder.bind(gpsQueue).to(exchange).with(properties.getRouting().getGpsKey());
	}

	@Bean
	public Binding onBinding(Queue onOffQueue, TopicExchange exchange) {
		return BindingBuilder.bind(onOffQueue).to(exchange).with(properties.getRouting().getOnKey());
	}

	@Bean
	public Binding offBinding(Queue onOffQueue, TopicExchange exchange) {
		return BindingBuilder.bind(onOffQueue).to(exchange).with(properties.getRouting().getOffKey());
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// ConnectionFactory는 Spring Boot에서 자동으로 생성해주므로 주입받아 사용한다.
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}
}
