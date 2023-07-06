package org.sadkowski.cinema.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "org.sadkowski.cinema.application.shows",
        "org.sadkowski.cinema.domain.shows.*",
        "org.sadkowski.cinema.infrastructure.shows",
})
public class CinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

}
