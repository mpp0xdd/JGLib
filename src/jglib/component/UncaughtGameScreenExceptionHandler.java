package jglib.component;

import java.lang.Thread.UncaughtExceptionHandler;
import jglib.base.GameError;
import jglib.base.GameLogger;

final class UncaughtGameScreenExceptionHandler implements UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    GameError error = new GameError(e);
    GameLogger.getLogger().error(error);
  }
}
