/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.helloworld;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

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

    router
        .route()
        .handler(
            routingContext -> {
              routingContext.response().putHeader("content-type", "text/html").end("Hello World!");
            });

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
