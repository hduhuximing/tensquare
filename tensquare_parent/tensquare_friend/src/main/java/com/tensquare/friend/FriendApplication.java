package com.tensquare.friend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

/**
 * SpringBootApplication：启动程序
 * EnableEurekaClient：注册服务
 * EnableDiscoveryClient：发现服务
 * EnableFeignClients：以Feign的方式发现服务
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendApplication.class);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	}
}
