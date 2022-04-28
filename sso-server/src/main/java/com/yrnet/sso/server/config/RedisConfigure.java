package com.yrnet.sso.server.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
@ConfigurationProperties(prefix = "sso.redis.cluster")
public class RedisConfigure extends CachingConfigurerSupport {

    private static int maxAttempts = 5;
    private String address;
    private String passWord;
    private int timeout;
    private int maxIdle;
    private int maxActive;
    private long maxWaitMillis;
    private boolean testOnBorrow;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    @Bean
    public JedisCluster jedisCluster(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
        genericObjectPoolConfig.setTestOnBorrow(testOnBorrow);
        Set<HostAndPort> nodes = new HashSet<>();
        String[] split = address.split(",");
        for (String s : split) {
            nodes.add(new HostAndPort(s.split(":")[0], Integer.parseInt(s.split(":")[1])));
        }
        if (StringUtils.isNotBlank(passWord)) {
            return new JedisCluster(nodes, timeout, timeout, maxAttempts, passWord, genericObjectPoolConfig);
        } else {
            return new JedisCluster(nodes, timeout, timeout, maxAttempts, genericObjectPoolConfig);
        }
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        String[] split = address.split(",");
        for (String s : split) {
            clusterConfig.addClusterNode(new RedisNode(s.split(":")[0], Integer.parseInt(s.split(":")[1])));
        }
        if(StringUtils.isNotBlank(passWord)){
            clusterConfig.setPassword(RedisPassword.of(passWord));
        }

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
        jedisClientConfiguration.usePooling();
        return new JedisConnectionFactory(clusterConfig, jedisClientConfiguration.build());
    }

    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        //使用 Jackson 序列化
        JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer(Object.class);
        // value 值的序列化采用 jacksonRedisSerializer
        template.setValueSerializer(jacksonRedisSerializer);
        template.setHashValueSerializer(jacksonRedisSerializer);
        // key 的序列化采用 StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory);
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            Arrays.stream(params).map(Object::toString).forEach(sb::append);
            return sb.toString();
        };
    }
}

class JacksonRedisSerializer<T> implements RedisSerializer<T> {
    private Class<T> clazz;
    private ObjectMapper mapper;

    JacksonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
        this.mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        try {
            return mapper.writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null||bytes.length <= 0) {
            return null;
        }
        try {
            return mapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
