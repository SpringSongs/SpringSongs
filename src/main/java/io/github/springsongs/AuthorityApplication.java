package io.github.springsongs;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.spring4all.swagger.EnableSwagger2Doc;

@EnableSwagger2Doc
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		})
public class AuthorityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorityApplication.class, args);
	}

}
