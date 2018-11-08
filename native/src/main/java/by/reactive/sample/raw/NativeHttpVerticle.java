package by.reactive.sample.raw;

import static by.reactive.sample.raw.NativeApp.WORKER_ADDRESS;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class NativeHttpVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/").handler(r -> {

            System.out.println("Post handler called with thread " + Thread.currentThread().getName());

            String bodyAsString = r.getBodyAsString();

            vertx.eventBus().send(WORKER_ADDRESS, bodyAsString,
                (Handler<AsyncResult<Message<String>>>) event -> {

                    if (event.succeeded()) {

                        r.response().end(event.result().body());
                    } else {

                        r.fail(event.cause());
                    }
                });
        });
        router.get("/").handler(r -> r.response().end("OK"));

        vertx.createHttpServer()
             .requestHandler(router::accept)
             .listen(8080, event -> {

                 if (event.succeeded()) {
                     startFuture.complete();
                 } else {
                     startFuture.fail(event.cause());
                 }
             });
    }
}
