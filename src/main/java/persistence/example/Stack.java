package persistence.example;

import redis.clients.jedis.Jedis;

public class Stack {

    private final Jedis connection;

    public Stack(Jedis connection) {
        this.connection = connection;
    }

    public void push(int value) {
        connection.lpush(String.valueOf(connection),String.valueOf(value));
    }

    public int pop() {
        if(isEmpty()){
            throw new UnderflowException();
        }
        return Integer.parseInt(connection.lpop(String.valueOf(connection)));
    }

    public boolean isEmpty() {
        boolean empty = true;
        if(connection.llen(String.valueOf(connection)) > 0){
            empty = false;
        }
        return empty;
    }

    public static class UnderflowException extends RuntimeException {
    }

}
