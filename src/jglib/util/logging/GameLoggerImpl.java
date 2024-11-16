package jglib.util.logging;

import jglib.base.GameThrowable;

final class GameLoggerImpl implements GameLogger {

  private final System.Logger logger;

  public GameLoggerImpl() {
    logger = System.getLogger(getClass().getName());
  }

  @Override
  public void warning(GameThrowable<?> gameThrowable) {
    log(System.Logger.Level.WARNING, gameThrowable);
  }

  @Override
  public void error(GameThrowable<?> gameThrowable) {
    log(System.Logger.Level.ERROR, gameThrowable);
  }

  private void log(System.Logger.Level level, GameThrowable<?> gameThrowable) {
    Throwable thrown = gameThrowable.asThrowable();
    logger.log(level, thrown.getMessage(), thrown);
  }
}
