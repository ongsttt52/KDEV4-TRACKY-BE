package kernel360trackybe.trackyhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://tracky-fe.vercel.app/", "http://localhost:5173")
                .allowedMethods("POST")
                .allowedHeaders("*")
                .allowCredentials(true)  // credentials를 false로 설정하면 allowedOrigins에 와일드카드 사용 가능
                .maxAge(3600);  // 1시간 동안 pre-flight 요청 결과를 캐시
    }
}
