/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.jwt;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;

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

    Router router = Router.router(vertx);

    // Create a JWT Auth Provider
    JWTAuth jwt =
        JWTAuth.create(
            vertx,
            new JWTAuthOptions()
                .setKeyStore(
                    new KeyStoreOptions()
                        .setType("jceks")
                        .setPath("keystore.jceks")
                        .setPassword("secret")));

    router
        .get("/api/newToken")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response()
                  .end(
                      jwt.generateToken(
                          new JsonObject(), new JWTOptions().setExpiresInSeconds(60)));
            });

    // protect the API
    router.route("/api/*").handler(JWTAuthHandler.create(jwt));

    // this is the secret API
    router
        .get("/api/protected")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response().end("a secret you should keep for yourself...");
            });

    // Serve the non private static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
