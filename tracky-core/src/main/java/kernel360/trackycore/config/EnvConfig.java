package kernel360.trackycore.config;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Configuration
public class EnvConfig {
	@PostConstruct
	public void loadEnv() {
		Dotenv dotenv = Dotenv.configure()
			.directory("./") // 루트에 있는 경우
			.filename(".env")
			.load();
		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
	}
}