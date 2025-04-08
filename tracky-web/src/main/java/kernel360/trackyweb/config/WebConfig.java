package kernel360.trackyweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("https://tracky-fe.vercel.app") // React (Vite) 주소
			.allowedOrigins("http://localhost:8080")
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(true);
	}
}
