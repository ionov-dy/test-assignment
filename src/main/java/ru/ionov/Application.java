package ru.ionov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.ionov.properties.IEXProperties;

@SpringBootApplication
@EnableConfigurationProperties(IEXProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
