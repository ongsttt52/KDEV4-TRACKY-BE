package kernel360trackybe.trackyhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "kernel360")
@EnableJpaRepositories(basePackages = {
	"kernel360.trackycore.core.infrastructure",
	"kernel360trackybe.trackyhub.car.infrastructor.repository"
})
@ComponentScan(basePackages = {"kernel360", "kernel360trackybe"})
public class TrackyHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyHubApplication.class, args);
	}

}
