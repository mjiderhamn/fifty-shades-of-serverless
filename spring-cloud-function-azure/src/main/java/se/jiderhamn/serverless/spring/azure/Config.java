package se.jiderhamn.serverless.spring.azure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.jiderhamn.serverless.TransformationService;

import java.util.function.Function;

/**
 * @author Mattias Jiderhamn
 */
@SpringBootApplication
public class Config {
	public static void main(String[] args) {
		SpringApplication.run(Config.class, args);
	}
}