package persistence.example;

import redis.clients.jedis.Jedis;

public class Stack {

    private final Jedis connection;

    public Stack(Jedis connection) {
        this.connection = connection;
    }

    public void push(int value) {
        // TODO: Implement it!
    }

    public int pop() {
        // TODO: Implement it!
        return -1;
    }

    public boolean isEmpty() {
        // TODO: Implement it!
        return false;
    }

    public static class UnderflowException extends RuntimeException {
    }

}
