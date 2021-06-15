/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.websockets;

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
        .webSocketHandler(ws -> ws.handler(ws::writeBinaryMessage))
        .requestHandler(
            req -> {
              if (req.uri().equals("/")) req.response().sendFile("ws.html");
            })
        .listen(8080);
  }
}
