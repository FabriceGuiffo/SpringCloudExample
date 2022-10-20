package com.example.configbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigbaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigbaseApplication.class, args);
	}

}
