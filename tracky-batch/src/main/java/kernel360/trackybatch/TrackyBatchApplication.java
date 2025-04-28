package kernel360.trackybatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "kernel360.trackybatch.infrastructure")
@EntityScan(basePackages = "kernel360.trackycore.core.domain.entity")
public class TrackyBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyBatchApplication.class, args);
	}

}
