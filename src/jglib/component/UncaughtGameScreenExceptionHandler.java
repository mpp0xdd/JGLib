package jglib.component;

import java.lang.Thread.UncaughtExceptionHandler;
import jglib.core.GameError;
import jglib.service.logging.GameLoggingService;

final class UncaughtGameScreenExceptionHandler implements UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    GameError error = new GameError(e);
    GameLoggingService.getLogger().error(error);
  }
}
