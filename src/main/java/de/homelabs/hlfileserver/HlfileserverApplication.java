package de.homelabs.hlfileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.homelabs.hlfileserver.entity.FSErrorCode;
import de.homelabs.hlfileserver.entity.FSResult;

@SpringBootApplication
public class HlfileserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(HlfileserverApplication.class, args);
		
	}
}
