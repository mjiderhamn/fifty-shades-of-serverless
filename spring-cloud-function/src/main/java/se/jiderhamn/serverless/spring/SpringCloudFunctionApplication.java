package se.jiderhamn.serverless.spring;

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
public class SpringCloudFunctionApplication {
  
  @Bean
  public Function<String, String> transform() {
    return input -> {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      TransformationService.transform(new ByteArrayInputStream(input.getBytes()), baos);
      return baos.toString();
    };
  }

  public static void main(String[] args) {
 		SpringApplication.run(SpringCloudFunctionApplication.class, args);
 	}
}