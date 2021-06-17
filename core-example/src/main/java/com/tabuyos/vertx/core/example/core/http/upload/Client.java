/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.http.upload;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

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
    HttpClient client = vertx.createHttpClient(new HttpClientOptions());
    client
        .request(HttpMethod.PUT, 8080, "localhost", "/someurl")
        .compose(
            req -> {
              String filename = "upload.txt";
              FileSystem fs = vertx.fileSystem();
              return fs.props(filename)
                  .compose(
                      props -> {
                        System.out.println("props is " + props);
                        long size = props.size();
                        req.headers().set("content-length", "" + size);
                        return fs.open(filename, new OpenOptions());
                      })
                  .compose(file -> req.send(file).map(HttpClientResponse::statusCode));
            })
        .onSuccess(statusCode -> System.out.println("Response " + statusCode))
        .onFailure(Throwable::printStackTrace);
  }
}
