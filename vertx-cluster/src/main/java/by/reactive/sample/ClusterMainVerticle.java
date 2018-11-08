package by.reactive.sample;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterMainVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(ClusterMainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    vertx
        .rxDeployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true))
        .subscribe(
            ok -> {
              vertx.deployVerticle(HttpVerticle.class.getName());

              log.info("Application started");
            },
            error -> {
              log.error("Cannot start application ", error);
            });
  }
}
