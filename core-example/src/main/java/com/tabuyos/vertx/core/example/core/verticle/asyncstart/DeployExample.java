/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.verticle.asyncstart;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class DeployExample extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(DeployExample.class);
  }

  @Override
  public void start() throws Exception {

    System.out.println("Main verticle has started, let's deploy some others...");

    // Deploy another instance and  want for it to start
    vertx.deployVerticle(
        "io.vertx.example.core.verticle.asyncstart.OtherVerticle",
        res -> {
          if (res.succeeded()) {

            String deploymentID = res.result();

            System.out.println("Other verticle deployed ok, deploymentID = " + deploymentID);

            vertx.undeploy(
                deploymentID,
                res2 -> {
                  if (res2.succeeded()) {
                    System.out.println("Undeployed ok!");
                  } else {
                    res2.cause().printStackTrace();
                  }
                });
          } else {
            res.cause().printStackTrace();
          }
        });
  }
}
