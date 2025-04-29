package kernel360.trackyweb.common.config;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(String.class, LocalDate.class, source -> {
			if (source.length() == 7) {
				YearMonth ym = YearMonth.parse(source, DateTimeFormatter.ofPattern("yyyy-MM"));
				return ym.atDay(1);
			} else {
				return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}
		});
	}
}