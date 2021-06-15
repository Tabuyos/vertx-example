/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.sendfile;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class SendFile extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(SendFile.class);
  }

  @Override
  public void start() throws Exception {

    // In reality it's highly recommend you use Vert.x-Web for applications like this.

    vertx
        .createHttpServer()
        .requestHandler(
            req -> {
              String filename = null;
              if (req.path().equals("/")) {
                filename = "index.html";
              } else if (req.path().equals("/page1.html")) {
                filename = "page1.html";
              } else if (req.path().equals("/page2.html")) {
                filename = "page2.html";
              } else {
                req.response().setStatusCode(404).end();
              }
              if (filename != null) {
                req.response().sendFile(filename);
              }
            })
        .listen(8080);
  }
}
