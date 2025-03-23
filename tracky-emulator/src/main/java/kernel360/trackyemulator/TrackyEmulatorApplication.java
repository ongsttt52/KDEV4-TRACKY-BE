package kernel360.trackyemulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TrackyEmulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackyEmulatorApplication.class, args);
    }
    @RestController
    public static class HelloController {

        @GetMapping("/")
        public String hello() {
            return "안녕-emulator server -jeenee-테스트중!!!! - 모듈 하나 수정테스트";
        }
    }

}
