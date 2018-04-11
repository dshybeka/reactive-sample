package by.reactive.sample;

import by.reactive.sample.conf.Initializer;
import by.reactive.sample.service.reactivex.ChatService;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {

        ChatService chatService = Initializer.applicationConfig(vertx).chatService();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.put("/").handler(r -> {

            JsonObject body = r.getBodyAsJson();
            chatService.rxAdd(body.getString("value"))
                       .subscribe(data -> r.response().end(data.toString()),
                           error -> r.fail(error));
        });
        router.get("/").handler(r -> {

            chatService.rxGetAll()
                       .subscribe(data -> r.response().end(data.toString()),
                           error -> r.fail(error));
        });

        vertx.createHttpServer()
             .requestHandler(router::accept)
             .rxListen(Integer.valueOf(System.getProperty("port", "7070")))
             .subscribe(ok -> startFuture.complete(), startFuture::fail);
    }
}
