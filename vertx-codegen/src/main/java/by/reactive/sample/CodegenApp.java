package by.reactive.sample;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.Vertx;

public class CodegenApp {

    public static final String CODEGEN_PROCESSING_ADDRESS = "codegen-worker";

    private static final Logger log = LoggerFactory.getLogger(CodegenApp.class);

    public static void main(String[] args) {

        log.info("Starting application");

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(CodegenMainVerticle.class.getName());
    }
}
