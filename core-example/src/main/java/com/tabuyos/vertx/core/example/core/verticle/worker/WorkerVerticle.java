/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.verticle.worker;

import io.vertx.core.AbstractVerticle;

/**
 * An example of worker verticle
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class WorkerVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    System.out.println("[Worker] Starting in " + Thread.currentThread().getName());

    vertx
        .eventBus()
        .<String>consumer(
            "sample.data",
            message -> {
              System.out.println("[Worker] Consuming data in " + Thread.currentThread().getName());
              String body = message.body();
              message.reply(body.toUpperCase());
            });
  }
}
