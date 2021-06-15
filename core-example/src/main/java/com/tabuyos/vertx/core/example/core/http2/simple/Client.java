/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http2.simple;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Client extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    // Note! in real-life you wouldn't often set trust all to true as it could leave you open to man
    // in the middle attacks.

    HttpClientOptions options =
        new HttpClientOptions()
            .setSsl(true)
            .setUseAlpn(true)
            .setProtocolVersion(HttpVersion.HTTP_2)
            .setTrustAll(true);

    HttpClient client = vertx.createHttpClient(options);
    client
        .request(HttpMethod.GET, 8080, "localhost", "/")
        .compose(
            req ->
                req.send()
                    .compose(
                        resp -> {
                          System.out.println("Got response " + resp.statusCode());
                          return resp.body();
                        }))
        .onSuccess(
            body -> {
              System.out.println("Got data " + body.toString("ISO-8859-1"));
            })
        .onFailure(
            err -> {
              err.printStackTrace();
            });
  }
}
