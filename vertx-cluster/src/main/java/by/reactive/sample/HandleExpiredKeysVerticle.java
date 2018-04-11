package by.reactive.sample;

import by.reactive.sample.utils.Utils;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleExpiredKeysVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(HandleExpiredKeysVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        vertx.eventBus().consumer("app.expired",
            (Handler<Message<String>>) event -> log.info("Handling message: {} on node: {} ", event.body(),
                Utils.getAddress()));
    }
}
