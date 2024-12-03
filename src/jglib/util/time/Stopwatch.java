package jglib.util.time;

import java.util.ArrayList;
import java.util.List;
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
  private final List<Long> splitTime;

  private Stopwatch(TimeUnit timeUnit, Clock clock) {
    this.timeUnit = Objects.requireNonNull(timeUnit);
    this.clock = Objects.requireNonNull(clock);
    this.splitTime = new ArrayList<>();
    reset();
  }

  public void reset() {
    startTime = stopTime = 0L;
    splitTime.clear();
  }

  public void start() {
    startTime = clock.currentTime();
    splitTime.clear();
  }

  public void stop() {
    stopTime = clock.currentTime();
  }

  public void split() {
    splitTime.add(clock.currentTime() - startTime);
  }

  public long splitTime(int no) {
    return splitTime.get(no);
  }

  public long splitTime(int no, TimeUnit timeUnit) {
    return timeUnit.convert(splitTime(no), this.timeUnit);
  }

  public void lap() {
    split();
  }

  public long lapTime(int no) {
    return no == 0 ? splitTime.get(0) : splitTime.get(no) - splitTime.get(no - 1);
  }

  public long lapTime(int no, TimeUnit timeUnit) {
    return timeUnit.convert(lapTime(no), this.timeUnit);
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
