/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.auth;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.auth.properties.PropertyFileAuthentication;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.FormLoginHandler;
import io.vertx.ext.web.handler.RedirectAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

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

    // We need sessions and request bodies
    router.route().handler(BodyHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

    // Simple auth service which uses a properties file for user/role info
    PropertyFileAuthentication authn =
        PropertyFileAuthentication.create(vertx, "vertx-users.properties");

    // Any requests to URI starting '/private/' require login
    router.route("/private/*").handler(RedirectAuthHandler.create(authn, "/loginpage.html"));

    // Serve the static private pages from directory 'private'
    router
        .route("/private/*")
        .handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("private"));

    // Handles the actual login
    router.route("/loginhandler").handler(FormLoginHandler.create(authn));

    // Implement logout
    router
        .route("/logout")
        .handler(
            context -> {
              context.clearUser();
              // Redirect back to the index page
              context.response().putHeader("location", "/").setStatusCode(302).end();
            });

    // Serve the non private static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
