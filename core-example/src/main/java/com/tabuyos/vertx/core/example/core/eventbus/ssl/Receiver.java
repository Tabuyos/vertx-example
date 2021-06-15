/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.eventbus.ssl;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.net.JksOptions;

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
    Runner.runClusteredExample(
        Receiver.class,
        new VertxOptions()
            .setEventBusOptions(
                new EventBusOptions()
                    .setSsl(true)
                    .setKeyStoreOptions(
                        new JksOptions().setPath("keystore.jks").setPassword("wibble"))
                    .setTrustStoreOptions(
                        new JksOptions().setPath("keystore.jks").setPassword("wibble"))));
  }

  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    eb.consumer(
        "ping-address",
        message -> {
          System.out.println("Received message: " + message.body());
          // Now send back reply
          message.reply("pong!");
        });

    System.out.println("Receiver ready!");
  }
}
