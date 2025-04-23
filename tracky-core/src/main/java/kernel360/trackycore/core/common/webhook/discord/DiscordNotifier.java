package kernel360.trackycore.core.common.webhook.discord;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import kernel360.trackycore.core.common.webhook.Notifier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnExpression("'${discord.webhook‑url}'.length() > 0")
public class DiscordNotifier implements Notifier {
	private final RestClient restClient;

	public DiscordNotifier(DiscordProperties discordProperties) {
		restClient = RestClient.create(discordProperties.getWebhookUrl());
	}

	@Override
	public void notify(Exception e) {
		var a = restClient.post()
			.contentType(MediaType.APPLICATION_JSON)
			.body(createMessage(e))
			.retrieve()
			.body(String.class);
		log.info("Discord notification sent: {}", a);
	}

	private DiscordMessage createMessage(Exception e) {
		String stackTrace = cutStackTrace(e);

		return new DiscordMessage(
			List.of(
				new DiscordMessage.Embed("ℹ️ 에러 요약", "```" + e.getMessage() + "```"),
				new DiscordMessage.Embed("📄 Stack Trace\n", "```" + stackTrace + "```")
			)
		);
	}

	private String cutStackTrace(Exception e) {
		String stackTrace = ExceptionUtils.getStackTrace(e);

		if (isTooLongMessage(stackTrace)) {
			stackTrace = stackTrace.substring(0, 2000) + "\n... (생략)";
		}

		return stackTrace;
	}

	private boolean isTooLongMessage(String stackTrace) {
		return stackTrace.length() > 2000;
	}
}
