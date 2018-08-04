package se.jiderhamn.serverless.spring.azure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.jiderhamn.serverless.TransformationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.function.Function;

/**
 * @author Mattias Jiderhamn
 */
@SpringBootApplication
public class Config {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Config.class, args);
	}

	/** TODO Replace with POF */
	@Bean
	public Function<byte[], byte[]> uppercase() {
		return input -> {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    TransformationService.transform(new ByteArrayInputStream(input), baos); // TODO Implement in service
  	  return baos.toByteArray();	   
		};
	}
}