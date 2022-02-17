/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.react;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
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

    // Allow events for the designated addresses in/out of the event bus bridge
    SockJSBridgeOptions opts =
        new SockJSBridgeOptions()
            .addInboundPermitted(new PermittedOptions().setAddress("chat.message"))
            .addOutboundPermitted(new PermittedOptions().setAddress("chat.message"));

    // Create the event bus bridge and add it to the router.
    SockJSHandler ebHandler = SockJSHandler.create(vertx);
    router.mountSubRouter("/eventbus", ebHandler.bridge(opts));

    // Create a router endpoint for the static content.
    router.route().handler(StaticHandler.create());

    // Start the web server and tell it to use the router to handle requests.
    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
