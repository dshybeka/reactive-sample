package by.reactive.sample;

import by.reactive.sample.converter.HeroConverter;
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
import reactor.ipc.netty.resources.LoopResources;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@Slf4j
public class AppConfig {

    @Bean
    public ExecutorService executorService() {

        return Executors.newFixedThreadPool(20, new ThreadFactoryBuilder().setNameFormat("worker-pool-%d").build());
    }

    @Bean
    public HeroConverter heroConverter() {

        return new HeroConverter();
    }

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {

        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.addServerCustomizers(builder -> builder.loopResources(LoopResources.create("evt-loop", 2, true)));

        return factory;
    }
}
