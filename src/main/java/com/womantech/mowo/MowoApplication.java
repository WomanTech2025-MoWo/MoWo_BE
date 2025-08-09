package com.womantech.mowo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MowoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MowoApplication.class, args);
	}

}
