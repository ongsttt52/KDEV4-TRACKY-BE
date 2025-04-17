package kernel360.trackyconsumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
public class RabbitMQProperties {

	private Batch batch;

	@Getter
	@Setter
	public static class Batch {
		private boolean defaultRequeueRejected;
		private boolean enabled;
		private int size;
		private long timeout;
		private boolean consumerBatchEnabled;
	}
}
