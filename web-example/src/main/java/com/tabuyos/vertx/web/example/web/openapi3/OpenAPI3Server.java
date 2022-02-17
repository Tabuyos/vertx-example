/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.web.example.web.openapi3;

import com.tabuyos.vertx.web.example.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.openapi.RouterBuilderOptions;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;

/**
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/18
 */
public class OpenAPI3Server extends AbstractVerticle {

  /**
   * Convenience method so you can run it in your IDE
   *
   * @param args args
   */
  public static void main(String[] args) {
    Runner.runExample(OpenAPI3Server.class);
  }

  private HttpServer server;

  @Override
  public void start() {
    // Load the api spec. This operation is asynchronous
    RouterBuilder.create(this.vertx, "petstore.yaml")
        .onFailure(
            Throwable::printStackTrace) // In case the contract loading failed print the stacktrace
        .onSuccess(
            routerBuilder -> {
              // Before router creation you can enable/disable various router factory behaviours
              RouterBuilderOptions factoryOptions =
                  new RouterBuilderOptions()
                      .setMountResponseContentTypeHandler(
                          true); // Mount ResponseContentTypeHandler automatically
              routerBuilder.setOptions(factoryOptions);

              // Setup an handler for listPets
              routerBuilder
                  .operation("listPets")
                  .handler(
                      routingContext -> {
                        // Load the parsed parameters
                        RequestParameters params =
                            routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);
                        // Handle listPets operation
                        RequestParameter limitParameter =
                            params.queryParameter(/* Parameter name */ "limit");
                        if (limitParameter != null) {
                          // limit parameter exists, use it!
                          Integer limit = limitParameter.getInteger();
                        } else {
                          // limit parameter doesn't exist (it's not required).
                          // If it's required you don't have to check if it's null!
                        }
                        routingContext.response().setStatusCode(200).end();
                      });

              // Create the router
              Router router = routerBuilder.createRouter();

              // Now you can use your Router instance
              server =
                  vertx
                      .createHttpServer(new HttpServerOptions().setPort(8080).setHost("localhost"))
                      .requestHandler(router);
              server
                  .listen()
                  .onSuccess(
                      server -> System.out.println("Server started on port " + server.actualPort()))
                  .onFailure(Throwable::printStackTrace);
            });
  }
}
