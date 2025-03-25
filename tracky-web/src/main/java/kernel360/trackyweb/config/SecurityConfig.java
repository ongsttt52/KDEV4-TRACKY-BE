package kernel360.trackyweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(Customizer.withDefaults()) // <- 더 이상 여기서 설정하지 않음
			.authorizeHttpRequests(registry -> registry
				.requestMatchers("/**").permitAll() // 전체 허용
			);
		return http.build();
	}
}
