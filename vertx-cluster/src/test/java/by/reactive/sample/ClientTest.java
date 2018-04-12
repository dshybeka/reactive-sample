package by.reactive.sample;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.HttpClientRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@Ignore
@RunWith(VertxUnitRunner.class)
public class ClientTest {

    @Test
    public void test(TestContext context) throws Exception {

        Async async = context.async();

        Vertx vertx = Vertx.vertx();

        HttpClient client = vertx.createHttpClient();

        HttpClientRequest localhost = client.post(8080, "localhost", "/", response -> {

            System.out.println("Post " + response.statusCode());


        });
        localhost.end("{\"name\":\"Flash\",\"skills\":[{\"name\":\"Hit\",\"type\":\"speed\",\"info\":{\"power\":5.778662130112895,\"source\":\"smth from movie 27\"}}],\"lastSeen\":15230182517491}");

        Thread.currentThread().sleep(100);

        HttpClientRequest localhost2 = client.post(8080, "localhost", "/", response -> {

            System.out.println("Post " + response.statusCode());


        });
        localhost2.end("{\"name\":\"Flash\",\"skills\":[{\"name\":\"Hit\",\"type\":\"speed\",\"info\":{\"power\":5.778662130112895,\"source\":\"smth from movie 27\"}}],\"lastSeen\":15230182517491}");

        HttpClientRequest localhost3 = client.post(8080, "localhost", "/", response -> {

            System.out.println("Post " + response.statusCode());

            async.complete();
        });
        localhost3.end("{\"name\":\"Flash\",\"skills\":[{\"name\":\"Hit\",\"type\":\"speed\",\"info\":{\"power\":5.778662130112895,\"source\":\"smth from movie 27\"}}],\"lastSeen\":15230182517491}");

        async.await(3000);
    }
}
