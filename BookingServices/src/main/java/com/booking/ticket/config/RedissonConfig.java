package com.booking.ticket.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
	
	@Value("${redis.host:localhost}")
	private String host;
	
	@Value("${redis.port:6379}")
	private String port;

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
           //   .setAddress("redis://redis-lock-server:6379")
              .setAddress("redis://"+host+":"+port)
              .setConnectionPoolSize(10)
              .setConnectionMinimumIdleSize(2);
        return Redisson.create(config);
    }
}

