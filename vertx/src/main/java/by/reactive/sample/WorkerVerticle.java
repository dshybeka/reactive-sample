package by.reactive.sample;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.model.Hero;
import io.vertx.core.Handler;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.Message;
import java.util.Optional;

public class WorkerVerticle extends AbstractVerticle {

    private final HeroConverter heroConverter;

    public WorkerVerticle() {
        this.heroConverter = new HeroConverter();
    }

    @Override
    public void start() {

        vertx.eventBus().consumer(App.PROCESSING_ADDRESS, (Handler<Message<String>>) event -> {

//            try {
//                Thread.currentThread().sleep(5_000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println("Worker handler called with thread " + Thread.currentThread().getName());

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
