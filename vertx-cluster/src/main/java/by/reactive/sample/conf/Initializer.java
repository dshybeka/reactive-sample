package by.reactive.sample.conf;

import io.vertx.reactivex.core.Vertx;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Initializer {

    private static AppConfig appConfig;

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
