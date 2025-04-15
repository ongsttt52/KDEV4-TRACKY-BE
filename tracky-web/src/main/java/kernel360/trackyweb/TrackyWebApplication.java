package kernel360.trackyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "kernel360")
@ComponentScan(basePackages = "kernel360")
public class TrackyWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrackyWebApplication.class, args);
	}
}
