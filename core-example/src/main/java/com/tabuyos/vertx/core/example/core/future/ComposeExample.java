/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.future;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class ComposeExample extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(ComposeExample.class);
  }

  @Override
  public void start() throws Exception {
    Future<String> future = anAsyncAction();
    future
        .compose(this::anotherAsyncAction)
        .onComplete(
            ar -> {
              if (ar.failed()) {
                System.out.println("Something bad happened");
                ar.cause().printStackTrace();
              } else {
                System.out.println("Result: " + ar.result());
              }
            });
  }

  private Future<String> anAsyncAction() {
    Promise<String> promise = Promise.promise();
    // mimic something that take times
    vertx.setTimer(100, l -> promise.complete("world"));
    return promise.future();
  }

  private Future<String> anotherAsyncAction(String name) {
    Promise<String> promise = Promise.promise();
    // mimic something that take times
    vertx.setTimer(100, l -> promise.complete("hello " + name));
    return promise.future();
  }
}
