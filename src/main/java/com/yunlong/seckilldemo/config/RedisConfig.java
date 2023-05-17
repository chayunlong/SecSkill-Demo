package com.yunlong.seckilldemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//redis配置类  实现用户的序列化
@Configuration  //不加这个注解的话 @Bean就会重复创建 加了这个注解 就会有一个代理管理 Bean对象 就不会重复创建bean对象
public class RedisConfig {


    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        //key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value的序列化  //GenericJackson2JsonRedisSerializer不需要传入参数就可以将对象转化为字符串
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        //hash类型key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //注入工厂连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;

    }
}
