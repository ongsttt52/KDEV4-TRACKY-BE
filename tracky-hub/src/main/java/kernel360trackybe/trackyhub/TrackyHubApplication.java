package kernel360trackybe.trackyhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = {
	"kernel360.trackycore.core.common",
})
// @EnableJpaRepositories를 이용하여 명시적으로 스캔할 패키지를 지정하면 지정된 패키지를 제외한 패키지는 무시한다.
// 따라서 자기 자신의 패키지로 명시적으로 지정해야 한다.
// @EnableJpaRepositories(basePackages = {
// 	"kernel360.trackycore.core.infrastructure.repository",
// 	"kernel360trackybe.trackyhub.infrastructure"
// })
@EnableJpaRepositories(basePackages = {
	"kernel360trackybe.trackyhub.infrastructure"
})
public class TrackyHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyHubApplication.class, args);
	}

}
