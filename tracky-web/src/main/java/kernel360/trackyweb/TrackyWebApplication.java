package kernel360.trackyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = {
	"kernel360.trackycore.core.infrastructure",
	"kernel360.trackyweb.member.infrastructure.entity",
	"kernel360.trackyweb.biz.presentation.entity"
})
public class TrackyWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyWebApplication.class, args);
	}

	@RestController
	public static class HelloController {

		@GetMapping("/")
		public String hello() {
			return "안녕-webserver-wonnie-테스트중!!!!";
		}
	}
}
