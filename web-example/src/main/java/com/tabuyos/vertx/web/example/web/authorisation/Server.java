/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.authorisation;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.authorization.PermissionBasedAuthorization;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.jwt.authorization.JWTAuthorization;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthorizationHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.ArrayList;
import java.util.List;

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

    // this route is excluded from the auth handler (it represents your login endpoint)
    router
        .get("/api/newToken")
        .handler(
            ctx -> {

              List<String> authorities = new ArrayList<>(ctx.request().params().getAll("authority"));

              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response()
                  .end(
                      jwt.generateToken(
                          new JsonObject(),
                          new JWTOptions().setExpiresInSeconds(60).setPermissions(authorities)));
            });

    JWTAuthHandler authnHandler = JWTAuthHandler.create(jwt);
    JWTAuthorization authProvider = JWTAuthorization.create("permissions");

    // protect the API (any authority is allowed)
    router.route("/api/*").handler(authnHandler);

    router
        .get("/api/protected")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response().end("this secret is not defcon!");
            });

    // protect the API (defcon1 authority is required)
    AuthorizationHandler defcon1Handler =
        AuthorizationHandler.create(PermissionBasedAuthorization.create("defcon1"))
            .addAuthorizationProvider(authProvider);
    router.route("/api/protected/defcon1").handler(defcon1Handler);

    router
        .get("/api/protected/defcon1")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response().end("this secret is defcon1!");
            });

    // protect the API (defcon2 authority is required)
    AuthorizationHandler defcon2Handler =
        AuthorizationHandler.create(PermissionBasedAuthorization.create("defcon2"))
            .addAuthorizationProvider(authProvider);
    router.route("/api/protected/defcon2").handler(defcon2Handler);

    router
        .get("/api/protected/defcon2")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response().end("this secret is defcon2!");
            });

    // protect the API (defcon3 authority is required)
    AuthorizationHandler defcon3Handler =
        AuthorizationHandler.create(PermissionBasedAuthorization.create("defcon3"))
            .addAuthorizationProvider(authProvider);
    router.route("/api/protected/defcon3").handler(defcon3Handler);

    router
        .get("/api/protected/defcon3")
        .handler(
            ctx -> {
              ctx.response().putHeader("Content-Type", "text/plain");
              ctx.response().end("this secret is defcon3!");
            });

    // Serve the non private static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
