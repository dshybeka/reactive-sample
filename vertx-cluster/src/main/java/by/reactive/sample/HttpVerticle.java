package by.reactive.sample;

import static by.reactive.sample.ClusterApp.INPUT_DATA_ADDRESS;

import by.reactive.sample.conf.AppConfig;
import by.reactive.sample.conf.Initializer;
import by.reactive.sample.service.reactivex.ChatService;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.core.eventbus.Message;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) {

    AppConfig appConfig = Initializer.applicationConfig(vertx);

    ChatService chatService = appConfig.chatService();

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route("/eventbus/*").handler(appConfig.sockJSHandler());

    EventBus eventBus = vertx.eventBus();
    eventBus.consumer(
        INPUT_DATA_ADDRESS,
        (Handler<Message<JsonObject>>)
            event -> {

              chatService
                  .rxAdd(event.body().getString("value"))
                  .subscribe(
                      data -> event.reply("ok"),
                      error -> event.fail(503, error.getMessage()));
            });

    router.route().handler(StaticHandler.create());
    vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .rxListen(Integer.valueOf(System.getProperty("port", "7070")))
        .subscribe(ok -> startFuture.complete(), startFuture::fail);
  }
}
