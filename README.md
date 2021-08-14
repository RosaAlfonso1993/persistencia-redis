# Redbee Academy - Redis

Ejemplos de redis utilizando la librería Jedis.

Ejecución: `./gradlew run`


## Comenzar

### Iniciar redis

```sh
docker run --name redis -p 6379:6379 -d redis:6-alpine
```

### Detener redis

```sh
docker stop redis
```

### Eliminar redis

```sh
docker rm redis
```


## Ejercicio

Implementar una pila (stack) que permita un acceso LIFO a los datos. Se disponibiliza la clase `Stack` para completar.
Para validar la implementación se cuentan con los tests unitarios ya implementados en la clase `StackTest`.


## Referencias

- Tecnologías:
    - [Redis](https://redis.io/)
    - [Jedis](https://github.com/redis/jedis)
- Conceptos:
    - [Introducción](https://www.baeldung.com/jedis-java-redis-client-library)
    - [Caching](https://redis.com/ebook/part-1-getting-started/chapter-2-anatomy-of-a-redis-web-application/2-4-database-row-caching/)
