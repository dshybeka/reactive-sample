package by.reactive.sample.config;

import by.reactive.sample.CodegenApp;
import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.service.HeroService;
import by.reactive.sample.service.HeroServiceVertxEBProxy;
import by.reactive.sample.service.impl.HeroServiceImpl;
import io.vertx.reactivex.core.Vertx;
import io.vertx.serviceproxy.ServiceBinder;

public class AppConfig {

    private final Vertx vertx;

    private final HeroConverter heroConverter;
    private final by.reactive.sample.service.reactivex.HeroService heroService;

    public AppConfig(Vertx vertx) {

        this.vertx = vertx;
        this.heroConverter = new HeroConverter();

        heroService = initHeroService(heroConverter);
    }

    public by.reactive.sample.service.reactivex.HeroService heroService() {

        return heroService;
    }

    private by.reactive.sample.service.reactivex.HeroService initHeroService(HeroConverter heroConverter) {

        ServiceBinder serviceBinder = new ServiceBinder(vertx.getDelegate());
        String address = CodegenApp.CODEGEN_PROCESSING_ADDRESS;
        serviceBinder.setAddress(address);
        serviceBinder.register(HeroService.class, new HeroServiceImpl(heroConverter));

        HeroServiceVertxEBProxy proxy = new HeroServiceVertxEBProxy(vertx.getDelegate(), address);

        return new by.reactive.sample.service.reactivex.HeroService(proxy);
    }
}
