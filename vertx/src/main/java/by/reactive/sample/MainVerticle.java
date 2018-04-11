package by.reactive.sample;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger("MainvVerticle.java");

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        log.info("Starting application");

        vertx.rxDeployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true))
             .subscribe(ok -> {

                 vertx.deployVerticle(HttpVerticle.class.getName());

                 log.info("Application started");
                 startFuture.complete();
             }, error -> {

                 log.error("Cannot start main verticle ", error);
                 startFuture.fail(error);
             });
    }
}
