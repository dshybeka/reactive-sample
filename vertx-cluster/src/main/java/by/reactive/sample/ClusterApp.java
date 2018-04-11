package by.reactive.sample;

import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import static java.lang.System.setProperty;

import io.vertx.core.logging.SLF4JLogDelegateFactory;
import io.vertx.reactivex.core.Vertx;

public class ClusterApp {

    public static void main(String[] args) {

        setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(ClusterMainVerticle.class.getName());
    }
}
