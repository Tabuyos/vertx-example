/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.websockets;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Client extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {
    HttpClient client = vertx.createHttpClient();

    client
        .webSocket(8080, "localhost", "/some-uri")
        .onSuccess(
            webSocket -> {
              webSocket.handler(
                  data -> {
                    System.out.println("Received data " + data.toString("ISO-8859-1"));
                    client.close();
                  });
              webSocket.writeBinaryMessage(Buffer.buffer("Hello world"));
            })
        .onFailure(Throwable::printStackTrace);
  }
}
