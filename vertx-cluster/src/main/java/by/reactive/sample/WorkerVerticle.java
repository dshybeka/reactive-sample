package by.reactive.sample;

import by.reactive.sample.conf.AppConfig;
import by.reactive.sample.conf.Initializer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

public class WorkerVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger("WorkerVerticle");

    @Override
    public void start() {

        AppConfig appConfig = Initializer.applicationConfig(vertx);

        log.info("App config created: " + appConfig);
    }
}
