package jglib.base;

import java.lang.Thread.UncaughtExceptionHandler;
import jglib.service.logging.GameLoggingService;

final class UncaughtGameExceptionHandler implements UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    GameError error = new GameError(e);
    GameLoggingService.getLogger().error(error);
  }
}
