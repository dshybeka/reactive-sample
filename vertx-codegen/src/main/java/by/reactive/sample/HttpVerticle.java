package by.reactive.sample;

import by.reactive.sample.config.Initializer;
import by.reactive.sample.service.reactivex.HeroService;
import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

public class HttpVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {

        HeroService heroService = Initializer.applicationConfig(vertx).heroService();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/").handler(r -> {

            System.out.println("Post handler called with thread " + Thread.currentThread().getName());

            String bodyAsString = r.getBodyAsString();

            heroService.rxHandleHero(bodyAsString)
                       .subscribe(ok -> r.response().end(ok),
                                  r::fail);
        });
        router.get("/").handler(r -> r.response().end("OK"));

        vertx.createHttpServer()
             .requestHandler(router::accept)
             .rxListen(8080)
             .subscribe(ok -> startFuture.complete(), startFuture::fail);
    }
}
