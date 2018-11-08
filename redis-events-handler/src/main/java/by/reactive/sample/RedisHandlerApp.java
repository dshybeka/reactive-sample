package by.reactive.sample;

import io.vertx.reactivex.core.Vertx;

public class RedisHandlerApp {

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(RedisClusterExpiredKeysHandlerVerticle.class.getName());
  }
}
