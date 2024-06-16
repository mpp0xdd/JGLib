package jglib.base;

public abstract class Game {

  protected Game() {
    Thread.currentThread().setUncaughtExceptionHandler(new UncaughtGameExceptionHandler());
  }

  protected abstract void start();
}
