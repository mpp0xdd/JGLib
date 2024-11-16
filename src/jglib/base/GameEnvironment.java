package jglib.base;

import jglib.util.logging.GameLoggingService;

public class GameEnvironment {

  private GameEnvironment() {}

  public static void initialize() {
    GameLoggingService.initialize();
  }
}
