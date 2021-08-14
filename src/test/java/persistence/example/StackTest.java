package persistence.example;

import com.github.fppt.jedismock.RedisServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.FlushMode;

import java.io.IOException;

@DisplayName("Redis backed Stack tests")
public class StackTest {

    private static RedisServer server = null;
    private static JedisPool jedisPool = null;
    private static Jedis connection = null;
    private static Stack stack = null;

    @BeforeAll
    static void setup() throws IOException {
        server = RedisServer.newRedisServer();
        server.start();
        jedisPool = new JedisPool(server.getHost(), server.getBindPort());
        connection = jedisPool.getResource();
    }

    @BeforeEach
    void createStack() {
        connection.flushAll(FlushMode.SYNC);
        stack = new Stack(connection);
    }

    @AfterAll
    static void cleanup() {
        if (connection != null) connection.close();
        if (jedisPool != null) jedisPool.close();
        if (server != null) server.stop();
    }

    @Test
    void aNewStackShouldBeEmpty() {
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    void stackShouldNotBeEmptyAfterOnePush() {
        stack.push(0);
        Assertions.assertFalse(stack.isEmpty());
    }

    @Test
    void throwsExceptionWhenEmptyStackIsPopped() {
        Assertions.assertThrows(Stack.UnderflowException.class, () -> stack.pop());
    }

    @Test
    void stackShouldBeEmptyAfterOnePushAndOnePop() {
        stack.push(0);
        stack.pop();
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    void stackShouldNotBeEmptyAfterTwoPushesAndOnePop() {
        stack.push(0);
        stack.push(0);
        stack.pop();
        Assertions.assertFalse(stack.isEmpty());
    }

    @Test
    void stackShouldPopXAfterPushingX() {
        stack.push(99);
        Assertions.assertEquals(99, stack.pop());

        stack.push(88);
        Assertions.assertEquals(88, stack.pop());
    }

    @Test
    void stackShouldPopYAndThenXAfterPushingXAndY() {
        stack.push(99);
        stack.push(88);
        Assertions.assertEquals(88, stack.pop());
        Assertions.assertEquals(99, stack.pop());
    }

}
