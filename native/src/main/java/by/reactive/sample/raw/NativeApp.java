package by.reactive.sample.raw;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class NativeApp {

  static final String WORKER_ADDRESS = "worker";

  private static Logger log = LoggerFactory.getLogger(NativeApp.class);

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    NativeMainVerticle mainVerticle = new NativeMainVerticle();

    vertx.deployVerticle(mainVerticle);
  }
}