package by.reactive.sample;

import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import static java.lang.System.setProperty;

import io.vertx.core.VertxOptions;
import io.vertx.core.logging.SLF4JLogDelegateFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClusterApp {

    //    private static final Logger LOG = LoggerFactory.getLogger(ClusterApp.class);

    public static final String CLUSTER_PROCESSING_ADDRESS = "cluster-worker";

    public static void main(String[] args) {

        setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());
        Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

        HazelcastClusterManager clusterManager = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

        Vertx.clusteredVertx(options, res -> {

            if (res.succeeded()) {

                Vertx vertx = res.result();
                vertx.deployVerticle(ClusterMainVerticle.class.getName());
            } else {

                logger.error("Cannot create vert.x cluster");
            }
        });
    }
}
