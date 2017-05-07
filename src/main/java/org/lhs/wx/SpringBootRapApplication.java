package org.lhs.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ImportResource(locations={"classpath:application-bean.xml"})
public class SpringBootRapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRapApplication.class, args);
	}

}
