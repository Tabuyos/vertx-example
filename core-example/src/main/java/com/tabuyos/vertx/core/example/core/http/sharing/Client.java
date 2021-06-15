/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.sharing;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

/**
 * A client illustrating the round robin made by vert.x. The client send a request to the server
 * periodically and print the received messages.
 *
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
    vertx.setPeriodic(
        1000,
        l -> {
          HttpClient client = vertx.createHttpClient();
          client
              .request(HttpMethod.GET, 8080, "localhost", "/")
              .compose(req -> req.send().compose(HttpClientResponse::body))
              .onSuccess(body -> System.out.println(body.toString("ISO-8859-1")))
              .onFailure(err -> err.printStackTrace());
        });
  }
}
