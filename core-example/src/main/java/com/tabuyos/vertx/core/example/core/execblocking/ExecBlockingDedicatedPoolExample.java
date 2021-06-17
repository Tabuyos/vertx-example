/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.execblocking;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.DeploymentOptions;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class ExecBlockingDedicatedPoolExample {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(
        ExecBlockingExample.class,
        new DeploymentOptions()
            .setWorkerPoolName("dedicated-pool")
            .setMaxWorkerExecuteTime(120000)
            .setWorkerPoolSize(5));
  }
}
