package by.reactive.sample;

import by.reactive.sample.converter.HeroConverter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
}
