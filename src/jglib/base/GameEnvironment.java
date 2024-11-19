package jglib.base;

import jglib.service.logging.GameLoggingService;

public class GameEnvironment {

  private GameEnvironment() {}

  public static void initialize() {
    GameLoggingService.initialize();
  }
}
