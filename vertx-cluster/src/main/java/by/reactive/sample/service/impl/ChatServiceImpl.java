package by.reactive.sample.service.impl;


import static by.reactive.sample.utils.Utils.getAddress;

import by.reactive.sample.service.ChatService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import java.util.Map;

public class ChatServiceImpl implements ChatService {

    private static final Logger log = LoggerFactory.getLogger("ChatServiceImpl");

    private static final String STORAGE_NAME = "chat";

    private final Vertx vertx;

    public ChatServiceImpl(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public ChatService getAll(Handler<AsyncResult<JsonArray>> handler) {

        logCurrentIp("get all");

        vertx.sharedData().rxGetClusterWideMap(STORAGE_NAME)
             .subscribe(ok -> {

                 ok.getDelegate().entries(new Handler<AsyncResult<Map>>() {
                     @Override
                     public void handle(AsyncResult<Map> dataHolder) {

                         if (dataHolder.succeeded()) {

                             handler.handle(Future.succeededFuture(new JsonArray().add(dataHolder.result())));
                         } else {

                             handler.handle(Future.succeededFuture(new JsonArray()
                                 .add(new JsonObject()
                                     .put("message", dataHolder.cause().getMessage())
                                 )));
                         }
                     }
                 });
             }, error -> {

                 handler.handle(Future.succeededFuture(new JsonArray()
                     .add(new JsonObject()
                         .put("message", error.getMessage())
                     )));
             });

        return this;
    }

    @Override
    public ChatService add(String data, Handler<AsyncResult<JsonObject>> handler) {

        logCurrentIp("add");

        vertx.sharedData().rxGetClusterWideMap(STORAGE_NAME)
             .flatMap(storage -> storage.rxPutIfAbsent(data + "_" + getAddress(), System.currentTimeMillis()))
             .subscribe(ok -> {

                 handler.handle(Future.succeededFuture(new JsonObject().put("added", true)));
             }, error -> {

                 handler.handle(Future.succeededFuture(new JsonObject()
                     .put("added", false)
                     .put("message", error.getMessage())
                 ));
             });

        return this;
    }

    private void logCurrentIp(String action) {

        String address = getAddress();

        log.info("Action " + action + " handled on " + address);
    }
}
