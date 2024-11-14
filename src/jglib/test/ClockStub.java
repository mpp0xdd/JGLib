package jglib.test;

import java.util.concurrent.TimeUnit;
import jglib.util.Clock;

class ClockStub implements Clock {

  public static ClockStub now(TimeUnit timeUnit) {
    ClockStub stub = newClock();
    stub.setCurrentTime(timeUnit.convert(System.nanoTime(), TimeUnit.NANOSECONDS));
    return stub;
  }

  public static ClockStub now() {
    return now(TimeUnit.NANOSECONDS);
  }

  public static ClockStub at(long time) {
    ClockStub stub = newClock();
    stub.setCurrentTime(time);
    return stub;
  }

  public static ClockStub newClock() {
    return new ClockStub();
  }

  private long currentTime;

  private ClockStub() {
    setCurrentTime(0L);
  }

  @Override
  public long currentTime() {
    return currentTime;
  }

  public void setCurrentTime(long currentTime) {
    this.currentTime = currentTime;
  }
}
