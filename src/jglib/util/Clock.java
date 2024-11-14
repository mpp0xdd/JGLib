package jglib.util;

@FunctionalInterface
public interface Clock {

  public static Clock nanoTimeClock() {
    return System::nanoTime;
  }

  public static Clock milliTimeClock() {
    return System::currentTimeMillis;
  }

  long currentTime();
}
