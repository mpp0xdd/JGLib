package jglib.util;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Stopwatch {

  public static Stopwatch create(TimeUnit timeUnit, Clock clock) {
    return new Stopwatch(timeUnit, clock);
  }

  public static Stopwatch nanoTimeWatch() {
    return new Stopwatch(TimeUnit.NANOSECONDS, Clock.nanoTimeClock());
  }

  private final TimeUnit timeUnit;
  private final Clock clock;
  private long startTime, stopTime;

  private Stopwatch(TimeUnit timeUnit, Clock clock) {
    this.timeUnit = Objects.requireNonNull(timeUnit);
    this.clock = Objects.requireNonNull(clock);
    reset();
  }

  public void reset() {
    startTime = stopTime = 0L;
  }

  public void start() {
    startTime = clock.currentTime();
  }

  public void stop() {
    stopTime = clock.currentTime();
  }

  public long measurementTime() {
    long result = stopTime - startTime;

    if (result < 0L) {
      throw new IllegalStateException();
    }
    return result;
  }

  public long measurementTime(TimeUnit timeUnit) {
    return timeUnit.convert(measurementTime(), this.timeUnit);
  }

  public long elapsedTime() {
    return clock.currentTime() - startTime;
  }

  public long elapsedTime(TimeUnit timeUnit) {
    return timeUnit.convert(elapsedTime(), this.timeUnit);
  }
}
