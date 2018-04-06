package by.reactive.sample.service;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

@ProxyGen
@VertxGen
public interface HeroService {

    @Fluent
    HeroService handleHero(String data, Handler<AsyncResult<String>> handler);

}
