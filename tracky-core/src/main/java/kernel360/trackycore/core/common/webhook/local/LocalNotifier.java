package kernel360.trackycore.core.common.webhook.local;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import kernel360.trackycore.core.common.webhook.Notifier;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "discord", name = "enable", havingValue = "false")
public class LocalNotifier implements Notifier {

	@Override
	public void notify(Exception e) {
		log.info("Local notification: {}", e.getMessage());
	}
}
