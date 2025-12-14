package com.dunhili.sasbank.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class to start the spring boot application.
 *
 * @author Brian Bowden
 */
@SpringBootApplication(scanBasePackages = {"com.dunhili.sasbank"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
