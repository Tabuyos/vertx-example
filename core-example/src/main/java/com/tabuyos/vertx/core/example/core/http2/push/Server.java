/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http2.push;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.PemKeyCertOptions;

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

    HttpServer server =
        vertx.createHttpServer(
            new HttpServerOptions()
                .setUseAlpn(true)
                .setSsl(true)
                .setPemKeyCertOptions(
                    new PemKeyCertOptions()
                        .setKeyPath("server-key.pem")
                        .setCertPath("server-cert.pem")));

    server.requestHandler(
        req -> {
          String path = req.path();
          HttpServerResponse resp = req.response();
          if ("/".equals(path)) {
            resp.push(
                HttpMethod.GET,
                "/script.js",
                ar -> {
                  if (ar.succeeded()) {
                    System.out.println("sending push");
                    HttpServerResponse pushedResp = ar.result();
                    pushedResp.sendFile("script.js");
                  } else {
                    // Sometimes Safari forbids push : "Server push not allowed to opposite
                    // endpoint."
                  }
                });

            resp.sendFile("index.html");
          } else if ("/script.js".equals(path)) {
            resp.sendFile("script.js");
          } else {
            System.out.println("Not found " + path);
            resp.setStatusCode(404).end();
          }
        });

    server.listen(
        8443,
        "localhost",
        ar -> {
          if (ar.succeeded()) {
            System.out.println("Server started");
          } else {
            ar.cause().printStackTrace();
          }
        });
  }
}
