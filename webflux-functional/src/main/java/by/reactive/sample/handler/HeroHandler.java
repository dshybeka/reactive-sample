package by.reactive.sample.handler;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.model.Hero;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class HeroHandler {

    private final ExecutorService executorService;

    private final HeroConverter heroConverter;

    public HeroHandler(ExecutorService executorService, HeroConverter heroConverter) {
        this.executorService = executorService;
        this.heroConverter = heroConverter;
    }

    public Mono<ServerResponse> handle(ServerRequest request) {

        log.info("Start handling post");

        Mono<String> result = request.bodyToMono(String.class)
                                     .publishOn(Schedulers.fromExecutor(executorService))
                                     .map(value -> {

                                         log.info("Handle post " + Thread.currentThread().getName());

                                         Optional<Hero> hero = heroConverter.fromJson(value);

                                         if (!hero.isPresent()) {
                                             throw new IllegalArgumentException();
                                         }

                                         Optional<String> maybeConvertedBack = heroConverter.toJson(hero.get());

                                         if (!maybeConvertedBack.isPresent()) {
                                             throw new IllegalArgumentException();
                                         }

                                         return maybeConvertedBack.get();
                                     });

        return ServerResponse.ok().body(result, String.class);
    }
}
