package kernel360.trackycore.core.common.webhook.local;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.webhook.Notifier;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnExpression("'${discord.webhook‑url}'.length() == 0")
public class LocalNotifier implements Notifier {

	@Override
	public void notify(Exception e) {
		log.info("Local notification: {}", e.getMessage());
	}
}
