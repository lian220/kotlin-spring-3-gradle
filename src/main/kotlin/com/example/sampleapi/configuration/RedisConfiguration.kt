package com.example.sampleapi.configuration

import com.example.sampleapi.service.user.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisStandaloneConfiguration

@Configuration
class RedisConfiguration: Log {
  @Value("\${spring.data.redis.host}")
  lateinit var host: String

  @Value("\${spring.data.redis.port}")
  lateinit var port: String

  companion object {
    private const val REDISSON_HOST_PREFIX = "redis://"
  }
  @Bean
  fun redisConnectionFactory(): LettuceConnectionFactory =
    LettuceConnectionFactory(host, port.toInt())

//  @Bean
//  fun webCacheConnectionFactory(): LettuceConnectionFactory {
//    val connectionFactory = LettuceConnectionFactory(RedisStandaloneConfiguration(host, port.toInt()))
//    connectionFactory.shareNativeConnection = true
//    return connectionFactory
//  }
//
//  @Bean
//  fun webCacheNonShareConnectionFactory(): LettuceConnectionFactory {
//    val connectionFactory = LettuceConnectionFactory(RedisStandaloneConfiguration(host, port.toInt()))
//    connectionFactory.shareNativeConnection = false
//    return connectionFactory
//  }
//
  @Bean
  fun redisTemplate(@Qualifier("redisConnectionFactory") redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
    val template = RedisTemplate<String, Any>()
    template.setConnectionFactory(redisConnectionFactory)
    template.keySerializer = StringRedisSerializer()
    return template
  }
//
//  @Bean
//  fun webCacheRedisTemplate(@Qualifier("webCacheConnectionFactory") webCacheConnectionFactory: LettuceConnectionFactory): RedisTemplate<String, Any> {
//    val template = RedisTemplate<String, Any>()
//    template.setConnectionFactory(webCacheConnectionFactory)
//    template.keySerializer = StringRedisSerializer()
//    return template
//  }
//
//  @Bean
//  fun webCacheTransactionalRedisTemplate(@Qualifier("webCacheNonShareConnectionFactory") webCacheNonShareConnectionFactory: LettuceConnectionFactory): RedisTemplate<String, Any> {
//    val template = RedisTemplate<String, Any>()
//    template.setConnectionFactory(webCacheNonShareConnectionFactory)
//    template.keySerializer = StringRedisSerializer()
//    return template
//  }
//
//  @Bean
//  fun springSessionDefaultRedisSerializer(): RedisSerializer<Any?>? {
//    return GenericJackson2JsonRedisSerializer()
//  }
//
  @Bean
  fun redissonClient(): RedissonClient? {
    var redisson: RedissonClient? = null
    val config = Config()
    config.useSingleServer().setAddress("$REDISSON_HOST_PREFIX$host:$port")
    logger.info(config.useSingleServer().address)
    redisson = Redisson.create(config)
    return redisson
  }
}
