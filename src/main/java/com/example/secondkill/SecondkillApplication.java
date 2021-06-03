package com.example.secondkill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.secondkill.dao")
@SpringBootApplication
public class SecondkillApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondkillApplication.class, args);
	}

}
