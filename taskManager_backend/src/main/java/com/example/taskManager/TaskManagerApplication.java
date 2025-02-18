package com.example.taskManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.taskManager.config.Auth0Properties;

@SpringBootApplication
@EnableConfigurationProperties( Auth0Properties.class )
public class TaskManagerApplication {

	public static void main( String[] args ) {
		SpringApplication.run( TaskManagerApplication.class, args );
	}

}
