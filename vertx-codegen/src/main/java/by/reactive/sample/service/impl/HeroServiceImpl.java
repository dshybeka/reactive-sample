package by.reactive.sample.service.impl;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.model.Hero;
import by.reactive.sample.service.HeroService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.serviceproxy.ServiceException;
import java.util.Optional;

public class HeroServiceImpl implements HeroService {

    private final HeroConverter heroConverter;

    public HeroServiceImpl(HeroConverter heroConverter) {
        this.heroConverter = heroConverter;
    }

    @Override
    public HeroService handleHero(String data, Handler<AsyncResult<String>> handler) {

        Optional<Hero> maybeHero = heroConverter.fromJson(data);

        if (!maybeHero.isPresent()) {

            handler.handle(Future.failedFuture(new ServiceException(503, "Cannot convert to model")));
        }

        Optional<String> convertedToJson = heroConverter.toJson(maybeHero.get());
        if (!convertedToJson.isPresent()) {

            handler.handle(Future.failedFuture(new ServiceException(503, "Cannot convert back to json")));
        }

        handler.handle(Future.succeededFuture(convertedToJson.get()));

        return this;
    }
}
