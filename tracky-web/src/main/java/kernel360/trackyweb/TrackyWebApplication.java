package kernel360.trackyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
	"kernel360.trackycore.core.infrastructure.entity" // ✅ 여기에 BizEntity 있는 패키지 지정
})
public class TrackyWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackyWebApplication.class, args);
	}
}
