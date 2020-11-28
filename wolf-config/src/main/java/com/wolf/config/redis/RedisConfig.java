package com.wolf.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        LOGGER.info("JedisConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
        template.setValueSerializer(jacksonSeial);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jacksonSeial);
        return template;
    }

    @Bean(name = "templateNew")
    public RedisTemplate<?, ?> getRedisTemplateNew() {
        RedisTemplate<?, ?> templateNew = new RedisTemplate<>();
        templateNew.setConnectionFactory(getConnectionFactory());
        return templateNew;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }


}
