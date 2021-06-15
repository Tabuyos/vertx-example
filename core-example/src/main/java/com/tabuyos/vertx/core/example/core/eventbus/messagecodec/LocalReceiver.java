/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.eventbus.messagecodec;

import com.tabuyos.vertx.core.example.core.eventbus.messagecodec.util.CustomMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/**
 * Local receiver
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class LocalReceiver extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    EventBus eventBus = getVertx().eventBus();

    // Does not have to register codec because sender already registered
    /*eventBus.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());*/

    // Receive message
    eventBus.consumer(
        "local-message-receiver",
        message -> {
          CustomMessage customMessage = (CustomMessage) message.body();

          System.out.println("Custom message received: " + customMessage.getSummary());

          // Replying is same as publishing
          CustomMessage replyMessage =
              new CustomMessage(200, "a00000002", "Message sent from local receiver!");
          message.reply(replyMessage);
        });
  }
}
