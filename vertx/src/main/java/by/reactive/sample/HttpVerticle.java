package by.reactive.sample;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.Message;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

public class HttpVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/").handler(r -> {

            String bodyAsString = r.getBodyAsString();

            vertx.eventBus().send(App.PROCESSING_ADDRESS, bodyAsString,
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
             .rxListen(8080)
             .subscribe(ok -> startFuture.complete(), startFuture::fail);
    }
}
