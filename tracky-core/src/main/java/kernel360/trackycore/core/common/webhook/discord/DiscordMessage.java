package kernel360.trackycore.core.common.webhook.discord;

import java.util.List;

public record DiscordMessage(List<Embed> embeds) {
	public record Embed(String title, String description) {
	}
}
