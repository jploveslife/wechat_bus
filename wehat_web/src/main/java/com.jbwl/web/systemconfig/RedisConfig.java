package com.jbwl.web.systemconfig;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: jipeng
 * @Description:
 * @Date: Created in 2018/7/14 23:29
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${wechat.bus.redis.hostName}")
    private String hostName;
    @Value("${wechat.bus.redis.port}")
    private Integer port;
    @Value("${wechat.bus.redis.password}")
    private String password;
    @Value("${wechat.bus.redis.timeout}")
    private Integer timeout;//客户端超时时间单位是毫秒 默认是2000
    @Value("${wechat.bus.redis.maxIdle}")
    private Integer maxIdle;//最大空闲数
    @Value("${wechat.bus.redis.maxTotal}")
    private Integer maxTotal;//控制一个pool可分配多少个jedis实例,用来替换上面的redis.maxActive,如果是jedis 2.4以后用该属性
    @Value("${wechat.bus.redis.maxWaitMillis}")
    private Integer maxWaitMillis;//最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。
    @Value("${wechat.bus.redis.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;//连接的最小空闲时间 默认1800000毫秒(30分钟)
    @Value("${wechat.bus.redis.numTestsPerEvictionRun}")
    private Integer numTestsPerEvictionRun;//每次释放连接的最大数目,默认3
    @Value("${wechat.bus.redis.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
    @Value("${wechat.bus.redis.testOnBorrow}")
    private Boolean testOnBorrow;//是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
    @Value("${wechat.bus.redis.testWhileIdle}")
    private Boolean testWhileIdle;//在空闲时检查有效性, 默认false



    /**
     * 自定义redis连接池配置
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);
        jedisPoolConfig.setMinIdle(10);




        return jedisPoolConfig;
    }

    /**
     * 基于自定义的redis连接池配置自定义redis链接工厂
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);

        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setTimeout(timeout);
        return jedisConnectionFactory;
    }


    /**
     * 基于自定义的redis连接工厂自动以redis模板
     * @param redisConnectionFactory
     * @param <T>
     * @param <D>
     * @return
     */
    @Bean
    public <T,D> RedisTemplate<T,D> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<T,D> redisTemplate = new RedisTemplate<T, D>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        /**
         * 做一些初始化设置
         * 主要是设置序列化对象
         * 和验证
         */
        redisTemplate.afterPropertiesSet();

        setSerializer(redisTemplate);
        return redisTemplate;
    }

    /**
     * redis模板的序列化
     * @param redisTemplate
     * @param <T>
     * @param <D>
     */
    private <T,D> void setSerializer(RedisTemplate<T,D> redisTemplate){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
    }

}
