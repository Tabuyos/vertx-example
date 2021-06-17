/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http2.customframes;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class Client extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(Client.class);
  }

  @Override
  public void start() throws Exception {

    // Note! in real-life you wouldn't often set trust all to true as it could leave you open to man
    // in the middle attacks.

    HttpClientOptions options =
        new HttpClientOptions()
            .setSsl(true)
            .setUseAlpn(true)
            .setProtocolVersion(HttpVersion.HTTP_2)
            .setTrustAll(true);

    HttpClient client = vertx.createHttpClient(options);

    client
        .request(HttpMethod.GET, 8443, "localhost", "/")
        .onSuccess(
            request -> {
              request
                  .response()
                  .onSuccess(
                      resp -> {

                        // Print custom frames received from server
                        resp.customFrameHandler(
                            frame -> {
                              System.out.println(
                                  "Got frame from server " + frame.payload().toString("UTF-8"));
                            });
                      });
              request
                  .sendHead()
                  .onSuccess(
                      v -> {

                        // Once head has been sent we can send custom frames
                        vertx.setPeriodic(
                            1000,
                          timerId -> {
                              System.out.println("Sending ping frame to server");
                              request.writeCustomFrame(10, 0, Buffer.buffer("ping"));
                            });
                      });
            });
  }
}
