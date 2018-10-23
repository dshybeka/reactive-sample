package by.reactive.sample.raw;

import static by.reactive.sample.raw.NativeApp.WORKER_ADDRESS;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.model.Hero;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import java.util.Optional;

public class NativeWorkerVerticle extends AbstractVerticle {

    private final HeroConverter heroConverter;

    public NativeWorkerVerticle() {
        this.heroConverter = new HeroConverter();
    }

    @Override
    public void start() {

        vertx.eventBus().consumer(WORKER_ADDRESS, (Handler<Message<String>>) event -> {

            System.out.println("Native worker handler called with thread " + Thread.currentThread().getName());

            String bodyAsString = event.body();

            Optional<Hero> maybeHero = heroConverter.fromJson(bodyAsString);

            if (!maybeHero.isPresent()) {
                event.fail(503, "Cannot parse to model");
            }

            Optional<String> convertedToJson = heroConverter.toJson(maybeHero.get());
            if (!convertedToJson.isPresent()) {
                event.fail(503, "Cannot convert back to json");
            }

            event.reply(convertedToJson.get());
        });
    }
}
