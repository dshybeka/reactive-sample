package by.reactive.sample;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import by.reactive.sample.converter.HeroConverter;
import by.reactive.sample.handler.HeroHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.ipc.netty.resources.LoopResources;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@Slf4j
public class FunctionalAppConfig {

    @Bean
    public ExecutorService executorService() {

        return Executors.newFixedThreadPool(20, new ThreadFactoryBuilder().setNameFormat("worker-pool-%d").build());
    }

    @Bean
    public HeroConverter heroConverter() {

        return new HeroConverter();
    }

    @Bean
    public RouterFunction<ServerResponse> post(HeroHandler heroHandler) {
        return route(POST("/"), heroHandler::handle);
    }

    @Bean
    public RouterFunction<ServerResponse> get(HeroHandler heroHandler) {
        return route(GET("/"), heroHandler::handle);
    }

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {

        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.addServerCustomizers(builder -> builder.loopResources(LoopResources.create("evt-loop", 2, true)));

        return factory;
    }
}
