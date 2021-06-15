/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.eventbus.pubsub;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Receiver extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runClusteredExample(Receiver.class);
  }

  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    eb.consumer(
        "news-feed",
        message -> System.out.println("Received news on consumer 1: " + message.body()));

    eb.consumer(
        "news-feed",
        message -> System.out.println("Received news on consumer 2: " + message.body()));

    eb.consumer(
        "news-feed",
        message -> System.out.println("Received news on consumer 3: " + message.body()));

    System.out.println("Ready!");
  }
}
