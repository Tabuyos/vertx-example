/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.staticsite;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * A simple static web server example
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/18
 */
public class Server extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {

    // We set this property to prevent Vert.x caching files loaded from the classpath on disk
    // This means if you edit the static files in your IDE then the next time they are served the
    // new ones will
    // be served without you having to restart the main()
    // This is only useful for development - do not use this in a production server
    System.setProperty("vertx.disableFileCaching", "true");

    Runner.runExample(Server.class);
  }

  @Override
  public void start() {

    Router router = Router.router(vertx);

    // Serve the static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router).listen(8080);

    System.out.println("Server is started");
  }
}
