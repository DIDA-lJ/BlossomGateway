package blossom.project.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

@SpringBootApplication
@RestController
public class Application {

    @GetMapping("/http-demo/ping")
    public String ping() {
        return "pong";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}