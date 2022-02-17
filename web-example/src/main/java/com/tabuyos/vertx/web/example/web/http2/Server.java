/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.http2;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;

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

    final Image image = new Image(vertx, "coin.png");

    Router router = Router.router(vertx);

    router
        .get("/")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/html").end(image.generateHTML(16));
            });

    router
        .get("/img/:x/:y")
        .handler(
            ctx -> {
              ctx.response()
                  .putHeader("Content-Type", "image/png")
                  .end(
                      image.getPixel(
                          Integer.parseInt(ctx.pathParam("x")),
                          Integer.parseInt(ctx.pathParam("y"))));
            });

    vertx
        .createHttpServer(
            new HttpServerOptions()
                .setSsl(true)
                .setUseAlpn(true)
                .setPemKeyCertOptions(
                    new PemKeyCertOptions()
                        .setKeyPath("tls/server-key.pem")
                        .setCertPath("tls/server-cert.pem")))
        .requestHandler(router)
        .listen(8443);
  }
}
