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
public class Sender extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runClusteredExample(Sender.class);
  }

  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    // Send a message every second

    vertx.setPeriodic(1000, v -> eb.publish("news-feed", "Some news!"));
  }
}
