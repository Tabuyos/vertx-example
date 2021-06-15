/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.proxy;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
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

    vertx
        .createHttpServer()
        .requestHandler(
            req -> {
              System.out.println("Got request " + req.uri());

              for (String name : req.headers().names()) {
                System.out.println(name + ": " + req.headers().get(name));
              }

              req.handler(data -> System.out.println("Got data " + data.toString("ISO-8859-1")));

              req.endHandler(
                  v -> {
                    // Now send back a response
                    req.response().setChunked(true);

                    for (int i = 0; i < 10; i++) {
                      req.response().write("server-data-chunk-" + i);
                    }

                    req.response().end();
                  });
            })
        .listen(8282);
  }
}
