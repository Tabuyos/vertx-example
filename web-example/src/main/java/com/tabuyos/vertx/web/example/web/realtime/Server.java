/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.realtime;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/18
 */
public class Server extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(Server.class);
  }

  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);

    // Allow outbound traffic to the news-feed address

    SockJSBridgeOptions options =
        new SockJSBridgeOptions()
            .addOutboundPermitted(new PermittedOptions().setAddress("news-feed"));
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
    Router subRouter =
        sockJSHandler.bridge(
            options,
            event -> {

              // You can also optionally provide a handler like this which will be passed any events
              // that occur on the bridge
              // You can use this for monitoring or logging, or to change the raw messages
              // in-flight.
              // It can also be used for fine grained access control.

              if (event.type() == BridgeEventType.SOCKET_CREATED) {
                System.out.println("A socket was created");
              }

              // This signals that it's ok to process the event
              event.complete(true);
            });
    router.mountSubRouter("/eventbus", subRouter);

    // Serve the static resources
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(8080);

    // Publish a message to the address "news-feed" every second
    vertx.setPeriodic(1000, t -> vertx.eventBus().publish("news-feed", "news from the server!"));
  }
}
