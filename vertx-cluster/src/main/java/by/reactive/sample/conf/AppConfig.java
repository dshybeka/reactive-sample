package by.reactive.sample.conf;

import by.reactive.sample.ClusterApp;
import by.reactive.sample.service.ChatServiceVertxEBProxy;
import by.reactive.sample.service.impl.ChatServiceImpl;
import by.reactive.sample.service.reactivex.ChatService;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.serviceproxy.ServiceBinder;

public class AppConfig {

  private final Vertx vertx;

  private final ChatService chatService;

  private final SockJSHandler sockJsHandler;

  public AppConfig(Vertx vertx) {

    this.vertx = vertx;

    this.chatService = initChatService();
    this.sockJsHandler = initSockJSHandler();
  }

  public ChatService chatService() {

    return chatService;
  }

  private ChatService initChatService() {

    ServiceBinder serviceBinder = new ServiceBinder(vertx.getDelegate());
    String address = ClusterApp.CLUSTER_PROCESSING_ADDRESS;
    serviceBinder.setAddress(address);
    serviceBinder.register(
        by.reactive.sample.service.ChatService.class, new ChatServiceImpl(vertx));

    ChatServiceVertxEBProxy proxy = new ChatServiceVertxEBProxy(vertx.getDelegate(), address);

    return new ChatService(proxy);
  }

  public SockJSHandler sockJSHandler() {

    return sockJsHandler;
  }

  private SockJSHandler initSockJSHandler() {

    BridgeOptions opts =
        new BridgeOptions()
            .addOutboundPermitted(new PermittedOptions().setAddressRegex("app.chat.data.*"))
            .addInboundPermitted(new PermittedOptions().setAddressRegex("app.chat.data.*"));

    return SockJSHandler.create(vertx).bridge(opts);
   }
}
