package com.ssafy.withme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ssafy.withme.global.openfeign")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
