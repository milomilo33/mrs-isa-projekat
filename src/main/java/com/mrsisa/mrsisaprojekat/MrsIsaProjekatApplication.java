package com.mrsisa.mrsisaprojekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MrsIsaProjekatApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MrsIsaProjekatApplication.class, args);
	}

}
