package com.talentchek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication 
public class TalentChekApp extends SpringBootServletInitializer {
	public static void main(String[] args) { 
		System.out.println("TalentChek App"); 
		SpringApplication.run(TalentChekApp.class, args);
	}
}
