/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.sharing;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

/**
 * An example illustrating the server sharing and round robin. The servers are identified using an
 * id. The HTTP Server Verticle is instantiated twice in the deployment options.
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Server extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(Server.class);
  }

  @Override
  public void start() throws Exception {
    vertx.deployVerticle(
        "com.tabuyos.vertx.core.example.core.http.sharing.HttpServerVerticle",
        new DeploymentOptions().setInstances(2));
  }
}
