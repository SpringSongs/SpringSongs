package io.github.springsongs;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import com.spring4all.swagger.EnableSwagger2Doc;

@EnableAsync
@EnableSwagger2Doc
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		})
public class AuthorityApplication {

	@PostConstruct
    void started() {
        //时区设置：中国上海
        //time.zone: "Asia/Shanghai"
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
	public static void main(String[] args) {
		SpringApplication.run(AuthorityApplication.class, args);
	}

}
