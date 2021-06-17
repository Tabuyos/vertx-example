/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.sharing;

import io.vertx.core.AbstractVerticle;

/**
 * A very simple HTTP server returning it's 'id' in the response.
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class HttpServerVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    vertx
        .createHttpServer()
        .requestHandler(
            req -> req.response()
                .putHeader("content-type", "text/html")
                .end("<html><body><h1>Hello from " + this + "</h1></body></html>"))
        .listen(8080);
  }
}
