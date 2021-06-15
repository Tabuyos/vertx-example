/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.ha;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

import java.lang.management.ManagementFactory;

/**
 * This is just a simple verticle creating a HTTP server. The served response contains an id
 * identifying the process for illustration purpose as it will change when the verticle is migrated.
 *
 * <p>The verticle is intended to be launched using the `-ha` option.
 *
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
    Launcher.main(new String[] {"run", Server.class.getName(), "-ha"});
  }

  @Override
  public void start() throws Exception {
    vertx
        .createHttpServer()
        .requestHandler(
            req -> {
              final String name = ManagementFactory.getRuntimeMXBean().getName();
              req.response().end("Happily served by " + name);
            })
        .listen(8080);
  }
}
