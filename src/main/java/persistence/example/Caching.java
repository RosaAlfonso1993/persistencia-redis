package persistence.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.example.domain.Employee;
import persistence.example.dto.EmployeeDTO;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.time.LocalDate;
import java.util.Random;

public class Caching {

    private static final String PREFIX = "persistencia:caching";
    private static final String KEY = PREFIX + ":" + "example_key";
    private static final Long TTL_IN_SECONDS = 120L;
    private static final int MAX_DELAY = 5000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;
    private final Random random;

    public Caching(JedisPool jedisPool, ObjectMapper objectMapper, Random random) {
        this.jedisPool = jedisPool;
        this.objectMapper = objectMapper;
        this.random = random;
    }

    public void execute() {
        try (Jedis connection = jedisPool.getResource()) {
            // Clear cache
            cleanup(connection);
            // First run
            doExecute(connection);
            // Second run
            doExecute(connection);
        } catch (Throwable e) {
            logger.error("Error in caching example", e);
        }
    }

    private void cleanup(Jedis connection) {
        final Long qDeletedElements = connection.del(KEY);
        logger.info("Deleted {} elements from cache", qDeletedElements);
    }

    private void doExecute(Jedis connection) throws JsonProcessingException, InterruptedException {
        String cachedValue = connection.get(KEY);
        if (cachedValue == null) {
            logger.info("Value not found in cache");
            final EmployeeDTO employeeDTO = executeExpensiveOperation();
            final String valueToCache = objectMapper.writeValueAsString(employeeDTO); // Serialize as JSON
            final SetParams setParams = new SetParams();
            setParams.nx(); // Set the key if it does not exist
            setParams.ex(TTL_IN_SECONDS); // The key will expire in TTL_IN_SECONDS seconds
            connection.set(KEY, valueToCache, setParams);
            logger.info("Employee {} stored in cache", employeeDTO);
            logger.info("Got employee {} as a result of an expensive computation", employeeDTO.toDomain());
        } else {
            logger.info("Value {} found in cache", cachedValue);
            final Employee employee =
                    objectMapper.readValue(cachedValue, EmployeeDTO.class) // Deserialize from JSON
                            .toDomain();
            logger.info("Got employee {} from cache", employee);
        }
    }

    private EmployeeDTO executeExpensiveOperation() throws InterruptedException {
        final int delay = random.nextInt(MAX_DELAY);
        logger.info("Executing expensive operation. It will take {} milliseconds", delay);
        Thread.sleep(delay);
        return new EmployeeDTO(
                1837,
                "Warren",
                "Robinett",
                "USA",
                LocalDate.of(2021, 8, 13)
        );
    }

}
