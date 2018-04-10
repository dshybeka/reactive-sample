package by.reactive.sample;

import io.vertx.reactivex.core.Vertx;

public class App {

    static final String PROCESSING_ADDRESS = "worker";

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(MainVerticle.class.getName());
    }
}
