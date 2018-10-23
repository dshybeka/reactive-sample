package by.reactive.sample.raw;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class NativeMainVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(NativeMainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    log.info("Starting application");

    vertx.deployVerticle(
        new NativeWorkerVerticle(),
        new DeploymentOptions().setWorker(true),
        event -> {
          if (event.succeeded()) {

            vertx.deployVerticle(new NativeHttpVerticle());

            log.info("Application started");
            startFuture.complete();
          } else {

            log.error("Cannot start main verticle ", event.cause());
            startFuture.fail(event.cause());
          }
        });
  }
}
