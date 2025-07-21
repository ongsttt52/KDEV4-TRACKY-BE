package kernel360trackybe.trackyhub.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
public class RabbitMQProperties {

	private Exchange exchange;
	private Queue queue;
	private Routing routing;

	@Getter
	@Setter
	public static class Exchange {
		private String carInfo;
		private String cycleInfo;
		private String deadLetter;
	}

	@Getter
	@Setter
	public static class Queue {
		private String gps;
		private String onoff;
		private String web;
		private String dlq;
	}

	@Getter
	@Setter
	public static class Routing {
		private String gpsKey;
		private String onKey;
		private String offKey;
		private String webKey;
		private String deadLetterKey;
	}
}
