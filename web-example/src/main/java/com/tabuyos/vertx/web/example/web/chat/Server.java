/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.chat;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * A {@link io.vertx.core.Verticle} which implements a simple, realtime, multiuser chat. Anyone can
 * connect to the chat application on port 8000 and type messages. The messages will be rebroadcast
 * to all connected users via the @{link EventBus} Websocket bridge.
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
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

    // Allow events for the designated addresses in/out of the event bus bridge
    SockJSBridgeOptions opts =
        new SockJSBridgeOptions()
            .addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"))
            .addOutboundPermitted(new PermittedOptions().setAddress("chat.to.client"));

    // Create the event bus bridge and add it to the router.
    SockJSHandler ebHandler = SockJSHandler.create(vertx);
    router.mountSubRouter("/eventbus", ebHandler.bridge(opts));

    // Create a router endpoint for the static content.
    router.route().handler(StaticHandler.create());

    // Start the web server and tell it to use the router to handle requests.
    vertx.createHttpServer().requestHandler(router).listen(8080);

    EventBus eb = vertx.eventBus();

    // Register to listen for messages coming IN to the server
    eb.consumer("chat.to.server")
        .handler(
            message -> {
              // Create a timestamp string
              String timestamp =
                  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
                      .format(Date.from(Instant.now()));
              // Send the message back out to all clients with the timestamp prepended.
              eb.publish("chat.to.client", timestamp + ": " + message.body());
            });
  }
}
