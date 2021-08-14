package persistence.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

public class Pipelining {

    private static final String KEY_PREFIX = "pipeline_example";
    private static final String STRING_KEY = KEY_PREFIX + ":string";
    private static final String HASH_KEY = KEY_PREFIX + ":hash";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    public Pipelining(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void execute() {
        try (Jedis connection = jedisPool.getResource()) {
            doExecute(connection);
        } catch (Throwable e) {
            logger.error("Error in pipelining example", e);
        }
    }

    private void doExecute(Jedis connection) {
        final var deletedKeys = connection.del(STRING_KEY, HASH_KEY);
        logger.info("Deleted {} preexisting keys", deletedKeys);

        final Pipeline pipeline = connection.pipelined();
        final var response1 = pipeline.set(STRING_KEY, "My string value");
        final var response2 = pipeline.hsetnx(HASH_KEY, "fieldA", "A value");
        final var response3 = pipeline.hsetnx(HASH_KEY, "fieldB", "Another value");

        pipeline.sync(); // Execute pipelined operations

        logger.info("Result of executing operation 1 {}", response1.get());
        logger.info("Result of executing operation 2 {}", response2.get());
        logger.info("Result of executing operation 3 {}", response3.get());

        final var hashedValue = connection.hgetAll(HASH_KEY);
        logger.info("Got value {} which was stored in a hash structure", hashedValue);
    }

}
