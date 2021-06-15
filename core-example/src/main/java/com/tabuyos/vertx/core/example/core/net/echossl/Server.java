/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.net.echossl;

import com.tabuyos.vertx.core.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.streams.Pump;

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

    NetServerOptions options =
        new NetServerOptions()
            .setSsl(true)
            .setKeyStoreOptions(
                new JksOptions().setPath("server-keystore.jks").setPassword("wibble"));

    vertx
        .createNetServer(options)
        .connectHandler(
            sock -> {

              // Create a pump
              Pump.pump(sock, sock).start();
            })
        .listen(1234);

    System.out.println("Echo server is now listening");
  }
}
