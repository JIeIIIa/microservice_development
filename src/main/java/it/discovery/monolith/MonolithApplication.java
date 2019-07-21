package it.discovery.monolith;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(value = {"it.discovery"})
@EntityScan(value = {"it.discovery"})
@ComponentScan(value = {"it.discovery"})
@AllArgsConstructor
@EnableScheduling
public class MonolithApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

}