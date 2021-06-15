/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.verticle.deploy;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class DeployPolyglotExample extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(DeployPolyglotExample.class);
  }

  @Override
  public void start() throws Exception {

    System.out.println("Main verticle has started, let's deploy A JS one...");

    // Deploy a verticle and don't wait for it to start,
    // the js verticle will use the noop2 npm module (which does nothing)
    // will invoke it and print a message
    vertx.deployVerticle("jsverticle.js");
  }
}
