package com.zxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rabbitmq.client.impl.AMQImpl.Queue;

@SpringBootApplication
public class DemoRabbitmqApplication {
	private static final String QUEUE = "MY_FIRSTRABBIT_QUEUE";

	public static void main(String[] args) {
		SpringApplication.run(DemoRabbitmqApplication.class, args);
	}

}
