/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.embed;

import io.vertx.core.Vertx;

/**
 * Embed server
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class EmbeddedServer {
  public static void main(String[] args) {
    // Create an HTTP server which simply returns "Hello World!" to each request.
    Vertx.vertx()
        .createHttpServer()
        .requestHandler(req -> req.response().end("Hello World!"))
        .listen(8080);
  }
}
