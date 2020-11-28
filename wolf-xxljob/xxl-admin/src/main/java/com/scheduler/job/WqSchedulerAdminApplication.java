package com.scheduler.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

/**
 * @author  2018-10-28 00:38:13
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.scheduler.**")
public class WqSchedulerAdminApplication {

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(WqSchedulerAdminApplication.class, args);

		System.out.println(new Date()+"  ===> xxl start success");

		String db_host = context.getEnvironment().getProperty("spring.datasource.url");
		System.out.println("db_host: "+db_host);

	}

}
