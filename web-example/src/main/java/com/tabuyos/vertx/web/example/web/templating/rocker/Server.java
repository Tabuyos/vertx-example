/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.templating.rocker;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.rocker.RockerTemplateEngine;

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

    final Router router = Router.router(vertx);

    // Populate context with data
    router
        .route()
        .handler(
            ctx -> {
              ctx.put("title", "Vert.x Web Example Using Rocker");
              ctx.put("name", "Rocker");
              ctx.next();
            });

    // Render a custom template.
    // Note: you need a compile-time generator for Rocker to work properly
    // See the pom.xml for an example
    router.route().handler(TemplateHandler.create(RockerTemplateEngine.create()));

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
