package jglib.test;

import java.util.ArrayList;
import java.util.List;
import jglib.base.GameThrowable;
import jglib.util.logging.GameLogger;

class GameLoggerStub implements GameLogger {

  public static GameLoggerStub create() {
    return new GameLoggerStub();
  }

  private final List<GameThrowable<?>> warning;
  private final List<GameThrowable<?>> error;

  private GameLoggerStub() {
    warning = new ArrayList<>();
    error = new ArrayList<>();
  }

  @Override
  public void warning(GameThrowable<?> gameThrowable) {
    warning.add(gameThrowable);
  }

  @Override
  public void error(GameThrowable<?> gameThrowable) {
    error.add(gameThrowable);
  }

  public List<GameThrowable<?>> getWarning() {
    return warning;
  }

  public <T extends Throwable> T getWarning(Class<T> clazz, int index) {
    return clazz.cast(getWarning().get(index));
  }

  public List<GameThrowable<?>> getError() {
    return error;
  }

  public <T extends Throwable> T getError(Class<T> clazz, int index) {
    return clazz.cast(getError().get(index));
  }

  public void initialize() {
    warning.clear();
    error.clear();
  }
}
