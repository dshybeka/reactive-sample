package by.reactive.sample;

import io.vertx.core.DeploymentOptions;
import io.vertx.reactivex.core.Vertx;

public class App {

    public static final String PROCESSING_ADDRESS = "worker";

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(HttpVerticle.class.getName());
        vertx.deployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true));
    }
}
