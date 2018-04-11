package by.reactive.sample.resource;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.model.Hero;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
public class HeroResource {

    private final ExecutorService executorService;
    private final HeroConverter heroConverter;

    public HeroResource(ExecutorService executorService, HeroConverter heroConverter) {

        this.executorService = executorService;
        this.heroConverter = heroConverter;
    }

    @PostMapping("/")
    public Mono<String> post(@RequestBody Mono<String> data) {

        return data
                   .subscribeOn(Schedulers.fromExecutor(executorService))
                   .map(value -> {

                       log.info("Handle post");

                       Optional<Hero> maybeHero = heroConverter.fromJson(value);

                       if (!maybeHero.isPresent()) {
                           throw new RuntimeException();
                       }

                       Optional<String> maybeConvertedBack = heroConverter.toJson(maybeHero.get());

                       if (!maybeConvertedBack.isPresent()) {
                           throw new RuntimeException();
                       }

                       return maybeConvertedBack.get();
                   });
    }

    @GetMapping("/")
    public String get() {

        return "OK";
    }
}
