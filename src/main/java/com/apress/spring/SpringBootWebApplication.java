package com.apress.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.apress.spring.service.JournalService;

@SpringBootApplication
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, 
		basePackages={"com.apress.spring.model"})
public class SpringBootWebApplication implements CommandLineRunner{
	
	@Autowired
	private JournalService journalService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		journalService.insertData();		
	}
}
