/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.eventbus.messagecodec;

import com.tabuyos.vertx.core.example.core.eventbus.messagecodec.util.CustomMessage;
import com.tabuyos.vertx.core.example.core.eventbus.messagecodec.util.CustomMessageCodec;
import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Cluster receiver
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class ClusterReceiver extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runClusteredExample(ClusterReceiver.class);
  }

  @Override
  public void start() throws Exception {
    EventBus eventBus = getVertx().eventBus();

    // Register codec for custom message
    eventBus.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());

    // Receive message
    eventBus.consumer(
        "cluster-message-receiver",
        message -> {
          CustomMessage customMessage = (CustomMessage) message.body();

          System.out.println("Custom message received: " + customMessage.getSummary());

          // Replying is same as publishing
          CustomMessage replyMessage =
              new CustomMessage(200, "a00000002", "Message sent from cluster receiver!");
          message.reply(replyMessage);
        });
  }
}
