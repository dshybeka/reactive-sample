package by.reactive.sample.service;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
public interface ChatService {

    @Fluent
    ChatService getAll(Handler<AsyncResult<JsonArray>> handler);

    @Fluent
    ChatService add(String data, Handler<AsyncResult<JsonObject>> handler);
}
