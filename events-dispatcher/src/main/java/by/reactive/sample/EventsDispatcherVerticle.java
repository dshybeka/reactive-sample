package by.reactive.sample;

import by.reactive.sample.utils.Utils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventsDispatcherVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        String host = System.getProperty("host", "localhost");
        Integer port = Integer.valueOf(System.getProperty("port", "6379"));

        RedisClient redisClient = RedisClient.create(RedisURI.builder()
                                                             .withHost(host)
                                                             .withPort(port)
                                                             .build());

        StatefulRedisPubSubConnection<String, String> pubSub =
            redisClient.connectPubSub();
        RedisPubSubReactiveCommands<String, String> pubSubCommands = pubSub.reactive();
        pubSub.sync().subscribe("__keyevent@0__:expired");

        pubSubCommands.observeChannels().doOnNext(message -> {

            log.info("Message: {} was retrieved by {}", message.getMessage(), Utils.getAddress());
            vertx.eventBus().send("app.expired", message.getMessage());
        }).subscribe();

        startFuture.complete();
    }
}
