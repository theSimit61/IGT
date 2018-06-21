package de.hsmannheim.igt.exercise.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * This is the default entry point for the Springboot application
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class})
public class ServerMainConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(ServerMainConfiguration.class, args);
	}

}
