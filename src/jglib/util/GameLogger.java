package jglib.util;

import jglib.base.GameThrowable;

public final class GameLogger {

  private static final GameLogger LOGGER = new GameLogger();

  public static GameLogger getLogger() {
    return LOGGER;
  }

  private final System.Logger logger;

  private GameLogger() {
    logger = System.getLogger(getClass().getName());
  }

  public void warning(GameThrowable<?> gameThrowable) {
    Throwable thrown = gameThrowable.toThrowable();
    logger.log(System.Logger.Level.WARNING, thrown.getMessage(), thrown);
  }
}
