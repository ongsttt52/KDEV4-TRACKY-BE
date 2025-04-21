package kernel360.trackyconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
	"kernel360.trackycore.core.domain",
})
@EnableJpaRepositories(basePackages = {
	"kernel360.trackycore.core.infrastructure",
	"kernel360.trackyconsumer"
})
@ComponentScan(basePackages = "kernel360")
public class TrackyConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyConsumerApplication.class, args);
	}

}
