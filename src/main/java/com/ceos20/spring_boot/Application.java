package com.ceos20.spring_boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {//CommandLineRunner:애플리케이션이 시작될 때 특정한 작업을 수행
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			// Spring Boot 에서 제공되는 Bean 확인
			String[] beanNames = ctx.getBeanDefinitionNames(); // 모든 빈의 이름을 배열로 가져옴
			Arrays.sort(beanNames);  // 알파벳순으로 정렬
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};

	}
}
