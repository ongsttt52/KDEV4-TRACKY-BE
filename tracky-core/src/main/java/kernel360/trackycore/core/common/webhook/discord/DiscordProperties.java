package kernel360.trackycore.core.common.webhook.discord;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "discord")
@Getter
@Setter
public class DiscordProperties {
	private String webhookUrl;
}
