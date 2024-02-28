package com.draiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.draiver")
public class KinesisTerminalToolApplication {


	public static void main(String[] args) {
		SpringApplication.run(KinesisTerminalToolApplication.class, args);
	}



}