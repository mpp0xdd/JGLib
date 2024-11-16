package jglib.util;

import jglib.util.time.Stopwatch;

@FunctionalInterface
public interface Executable {

  public static Executable of(Executable executable) {
    return executable;
  }

  void execute();

  default void execute(Stopwatch stopwatch) {
    stopwatch.start();
    this.execute();
    stopwatch.stop();
  }
}
