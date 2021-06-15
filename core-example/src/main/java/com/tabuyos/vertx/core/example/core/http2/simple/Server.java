/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http2.simple;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
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

    server
        .requestHandler(
            req -> {
              req.response()
                  .putHeader("content-type", "text/html")
                  .end(
                      "<html><body>"
                          + "<h1>Hello from vert.x!</h1>"
                          + "<p>version = "
                          + req.version()
                          + "</p>"
                          + "</body></html>");
            })
        .listen(8443);
  }
}
