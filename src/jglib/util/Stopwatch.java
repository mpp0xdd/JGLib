package jglib.util;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

public class Stopwatch {

  public static Stopwatch create(TimeUnit timeUnit, LongSupplier clock) {
    return new Stopwatch(timeUnit, clock);
  }

  public static Stopwatch nanoTimeWatch() {
    return new Stopwatch(TimeUnit.NANOSECONDS, System::nanoTime);
  }

  private final TimeUnit timeUnit;
  private final LongSupplier clock;
  private long startTime, stopTime;

  private Stopwatch(TimeUnit timeUnit, LongSupplier clock) {
    this.timeUnit = Objects.requireNonNull(timeUnit);
    this.clock = Objects.requireNonNull(clock);
    startTime = stopTime = 0L;
  }

  public void start() {
    startTime = currentTime();
  }

  public void stop() {
    stopTime = currentTime();
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
    return currentTime() - startTime;
  }

  public long elapsedTime(TimeUnit timeUnit) {
    return timeUnit.convert(elapsedTime(), this.timeUnit);
  }

  private long currentTime() {
    return clock.getAsLong();
  }
}
