package comy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class SpeakApp {
    public static void main(String[] args) {
        SpringApplication.run(SpeakApp.class, args);
    }
}



