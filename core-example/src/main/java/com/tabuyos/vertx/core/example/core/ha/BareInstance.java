/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.vertx.core.example.core.ha;

import io.vertx.core.Launcher;

/**
 * Just start a bare instance of vert.x . It will receive the Server verticle when the process is
 * killed.
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/15
 */
public class BareInstance {

  public static void main(String[] args) {
    Launcher.main(new String[] {"bare"});
  }
}
