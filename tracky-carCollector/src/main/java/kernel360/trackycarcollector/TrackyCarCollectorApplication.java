package kernel360.trackycarcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TrackyCarCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackyCarCollectorApplication.class, args);
    }

    @RestController
    public static class HelloController {

        @GetMapping("/")
        public String hello() {
            return "안녕";
        }
    }
}
