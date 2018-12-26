package com.gdsd.sellpurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class SellpurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellpurchaseApplication.class, args);
	}

}

