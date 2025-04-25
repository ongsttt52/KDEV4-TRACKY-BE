package kernel360trackybe.trackybatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class TrackyBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyBatchApplication.class, args);
	}

}
