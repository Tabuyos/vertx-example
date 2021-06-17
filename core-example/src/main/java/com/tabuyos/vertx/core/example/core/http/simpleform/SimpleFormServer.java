/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.simpleform;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;

/**
 * NOTE! It's recommended to use Vert.x-Web for examples like this
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class SimpleFormServer extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(SimpleFormServer.class);
  }

  @Override
  public void start() throws Exception {
    vertx
        .createHttpServer()
        .requestHandler(
            req -> {
              if ("/".equals(req.uri())) {
                // Serve the index page
                req.response().sendFile("index.html");
              } else if (req.uri().startsWith("/form")) {
                req.response().setChunked(true);
                req.setExpectMultipart(true);
                req.endHandler(
                    v -> {
                      for (String attr : req.formAttributes().names()) {
                        req.response()
                            .write(
                                "Got attr " + attr + " : " + req.formAttributes().get(attr) + "\n");
                      }
                      req.response().end();
                    });
              } else {
                req.response().setStatusCode(404).end();
              }
            })
        .listen(8080);
  }
}
