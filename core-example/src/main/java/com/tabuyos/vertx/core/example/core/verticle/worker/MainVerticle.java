/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.verticle.worker;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

/**
 * An example illustrating how worker verticles can be deployed and how to interact with them.
 *
 * <p>This example prints the name of the current thread at various locations to exhibit the event
 * loop <-> worker thread switches.
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class MainVerticle extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(MainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    System.out.println("[Main] Running in " + Thread.currentThread().getName());
    vertx.deployVerticle(
        "io.vertx.example.core.verticle.worker.WorkerVerticle",
        new DeploymentOptions().setWorker(true));

    vertx
        .eventBus()
        .request(
            "sample.data",
            "hello vert.x",
            r -> {
              System.out.println(
                  "[Main] Receiving reply ' "
                      + r.result().body()
                      + "' in "
                      + Thread.currentThread().getName());
            });
  }
}
