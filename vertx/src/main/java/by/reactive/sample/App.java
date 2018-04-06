package by.reactive.sample;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;

public class App {

    public static final String PROCESSING_ADDRESS = "worker";

    private static final Logger log = LoggerFactory.getLogger("App.java");

    public static void main(String[] args) {

        log.info("Starting application");

        Vertx vertx = Vertx.vertx();

        vertx.rxDeployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true))
             .subscribe(ok -> {

                 vertx.deployVerticle(HttpVerticle.class.getName());

                 log.info("Application started");
             }, error -> {

                 log.error("Cannot start application ", error);
             });
    }
}
