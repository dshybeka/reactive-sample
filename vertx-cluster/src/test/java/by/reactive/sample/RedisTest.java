package by.reactive.sample;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import reactor.core.publisher.Mono;

public class RedisTest {

    @org.junit.Test
    public void name() throws Exception {
        //  given:

        RedisClient redisClient = RedisClient.create("redis://10.5.12.243:32225");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> commands = connect.sync();

        for (int i = 0; i < 1000; i++) {

            commands.setex("testk_" + i, 30, "some value with " + i);
        }

        StatefulRedisPubSubConnection<String, String> pubSub =
            redisClient.connectPubSub();
        RedisPubSubReactiveCommands<String, String> pubSubCommands = pubSub.reactive();
        Mono<Void> subscribed = pubSubCommands.subscribe("__keyevent@0__:expired");
        subscribed.subscribe(ok -> {

            pubSubCommands.observeChannels().doOnNext(message -> {

                System.out.println("Message retrieved!");
            });
        }, error -> {

            error.printStackTrace();
        });

        connect.close();
        redisClient.shutdown();
    }
}
