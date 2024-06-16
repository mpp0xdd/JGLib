package jglib.base;

import java.lang.Thread.UncaughtExceptionHandler;
import jglib.util.GameLogger;

final class UncaughtGameExceptionHandler implements UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    GameError error = new GameError(e);
    GameLogger.getLogger().error(error);
  }
}
