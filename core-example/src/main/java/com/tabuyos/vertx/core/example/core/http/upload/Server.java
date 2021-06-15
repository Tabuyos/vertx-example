/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.upload;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.streams.Pump;

import java.util.UUID;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Server extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(Server.class);
  }

  @Override
  public void start() throws Exception {
    vertx
        .createHttpServer()
        .requestHandler(
            req -> {
              req.pause();
              String filename = UUID.randomUUID() + ".uploaded";
              vertx
                  .fileSystem()
                  .open(
                      filename,
                      new OpenOptions(),
                      ares -> {
                        AsyncFile file = ares.result();
                        Pump pump = Pump.pump(req, file);
                        req.endHandler(
                            v1 ->
                                file.close(
                                    v2 -> {
                                      System.out.println("Uploaded to " + filename);
                                      req.response().end();
                                    }));
                        pump.start();
                        req.resume();
                      });
            })
        .listen(8080);
  }
}
