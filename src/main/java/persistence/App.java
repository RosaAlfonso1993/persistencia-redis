package persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.example.Caching;
import persistence.example.Pipelining;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;

public class App {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new App().execute();
    }

    public void execute() {
        JedisPool jedisPool = null;
        try {
            jedisPool = buildPool();
            final var objectMapper = buildObjectMapper();
            final var random = new Random();
            final var caching = new Caching(jedisPool, objectMapper, random);
            final var pipelining = new Pipelining(jedisPool);
            caching.execute();
            pipelining.execute();
        } catch (Throwable ex) {
            logger.error("Error running application", ex);
        } finally {
            if (jedisPool != null) {
                jedisPool.destroy();
            }
        }
    }

    private JedisPool buildPool() {
        final var poolConfig = buildConfig();
        return new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);
    }

    private JedisPoolConfig buildConfig() {
        final var config = new JedisPoolConfig();
        config.setMaxTotal(3);
        config.setMaxWaitMillis(10000L);
        config.setJmxEnabled(false);
        return config;
    }

    private ObjectMapper buildObjectMapper() {
        final var objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
