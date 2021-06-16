/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.util;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Verticle Runner
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Runner {
  private static final String CORE_EXAMPLES_DIR = "core-example";
  private static final String CORE_EXAMPLES_JAVA_DIR = CORE_EXAMPLES_DIR + "/src/main/java/";

  public static void runClusteredExample(Class<?> clazz) {
    runExample(CORE_EXAMPLES_JAVA_DIR, clazz, new VertxOptions(), null, true);
  }

  public static void runClusteredExample(Class<?> clazz, VertxOptions options) {
    runExample(CORE_EXAMPLES_JAVA_DIR, clazz, options, null, true);
  }

  public static void runExample(Class<?> clazz) {
    runExample(CORE_EXAMPLES_JAVA_DIR, clazz, new VertxOptions(), null, false);
  }

  public static void runExample(Class<?> clazz, DeploymentOptions options) {
    runExample(CORE_EXAMPLES_JAVA_DIR, clazz, new VertxOptions(), options, false);
  }

  public static void runExample(
      String exampleDir,
      Class<?> clazz,
      VertxOptions options,
      DeploymentOptions deploymentOptions,
      boolean clustered) {
    runExample(
        exampleDir + clazz.getPackage().getName().replace(".", "/"),
        clazz.getName(),
        options,
        deploymentOptions,
        clustered);
  }

  public static void runExample(
      String exampleDir,
      String verticleId,
      VertxOptions options,
      DeploymentOptions deploymentOptions,
      boolean clustered) {
    if (options == null) {
      // Default parameter
      options = new VertxOptions();
    }
    // Smart cwd detection

    // Based on the current directory (.) and the desired directory (exampleDir), we try to compute
    // the vertx.cwd
    // directory:
    try {
      // We need to use the canonical file. Without the file name is .
      File current = new File(".").getCanonicalFile();
      if (exampleDir.startsWith(current.getName()) && !exampleDir.equals(current.getName())) {
        exampleDir = exampleDir.substring(current.getName().length() + 1);
      }
    } catch (IOException e) {
      // Ignore it.
    }

    System.setProperty("vertx.cwd", exampleDir);
    Consumer<Vertx> runner =
        vertx -> {
          try {
            if (deploymentOptions != null) {
              vertx.deployVerticle(verticleId, deploymentOptions);
            } else {
              vertx.deployVerticle(verticleId);
            }
          } catch (Throwable t) {
            t.printStackTrace();
          }
        };
    if (clustered) {
      Vertx.clusteredVertx(
          options,
          res -> {
            if (res.succeeded()) {
              Vertx vertx = res.result();
              runner.accept(vertx);
            } else {
              res.cause().printStackTrace();
            }
          });
    } else {
      Vertx vertx = Vertx.vertx(options);
      runner.accept(vertx);
    }
  }
}
