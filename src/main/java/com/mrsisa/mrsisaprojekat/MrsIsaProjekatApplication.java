package com.mrsisa.mrsisaprojekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class MrsIsaProjekatApplication {

//	@Bean
//	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
//		return hemf.getSessionFactory();
//	}

	public static void main(String[] args) {
		SpringApplication.run(MrsIsaProjekatApplication.class, args);
	}

}
