package by.reactive.sample;

import io.vertx.reactivex.core.Vertx;

public class EventDispatcherMain {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(EventsDispatcherVerticle.class.getName());
    }
}
