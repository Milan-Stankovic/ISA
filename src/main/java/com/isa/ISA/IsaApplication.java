package com.isa.ISA;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class IsaApplication {


	public static void main(String[] args) {

		SpringApplication.run(IsaApplication.class, args);



	}


}
