package by.reactive.sample.config;

import io.vertx.reactivex.core.Vertx;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Initializer {

    private volatile static AppConfig appConfig;

    /**
     * Create app config singleton instance.
     */
    public static AppConfig applicationConfig(Vertx vertx) {

        if (appConfig == null) {

            synchronized (AppConfig.class) {
                if (appConfig == null) {

                    appConfig = new AppConfig(vertx);
                }
            }
        }
        return appConfig;
    }
}
