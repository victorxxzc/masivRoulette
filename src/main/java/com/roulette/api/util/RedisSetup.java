package com.roulette.api.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
/**
 *Redis configuration
 * @author Victor Buritica
 *
 */
@Configuration
@EnableRedisRepositories
public class RedisSetup {
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		return new LettuceConnectionFactory();
	}
	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}
}
