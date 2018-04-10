package by.reactive.sample;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusterApp {

    private static final Logger log = LoggerFactory.getLogger("App.java");

    public static final String CLUSTER_PROCESSING_ADDRESS = "cluster-worker";

    public static void main(String[] args) {

        HazelcastClusterManager clusterManager = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

        Vertx.clusteredVertx(options, res -> {

            if (res.succeeded()) {

                Vertx vertx = res.result();
                vertx.rxDeployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true))
                     .subscribe(ok -> {

                         vertx.deployVerticle(HttpVerticle.class.getName());

                         log.info("Application started");
                     }, error -> {

                         log.error("Cannot start application ", error);
                     });
            } else {

                log.error("Cannot create vert.x cluster");
            }
        });
    }
}
