/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.cookie;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;
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

    // on every path increment the counter
    router
        .route()
        .handler(
            ctx -> {
              Cookie someCookie = ctx.getCookie("visits");

              long visits = 0;
              if (someCookie != null) {
                String cookieValue = someCookie.getValue();
                try {
                  visits = Long.parseLong(cookieValue);
                } catch (NumberFormatException e) {
                  visits = 0;
                }
              }

              // increment the tracking
              visits++;

              // Add a cookie - this will get written back in the response automatically
              ctx.addCookie(Cookie.cookie("visits", "" + visits));

              ctx.next();
            });

    // Serve the static resources
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }
}
