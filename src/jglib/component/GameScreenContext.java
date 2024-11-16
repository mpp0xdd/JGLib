package jglib.component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import jglib.util.Executable;
import jglib.util.FrameRate;
import jglib.util.GameUtilities;
import jglib.util.time.Clock;
import jglib.util.time.Stopwatch;

class GameScreenContext {

  private static final int MAX_DELAY_COUNT = 16;
  private static final int MAX_SKIP_COUNT = 5;

  public static GameScreenContext nanoTimeContext(FrameRate frameRate) {
    return new GameScreenContext(frameRate.toDuration().toNanos(), TimeUnit.NANOSECONDS);
  }

  private final long duration;
  private final TimeUnit timeUnit;
  private final Stopwatch stopwatch;

  private long sleepTime;
  private long overSleepTime;
  private int delayCount;
  private long excessSleepTime;

  private GameScreenContext(long duration, TimeUnit timeUnit) {
    this.duration = duration;
    this.timeUnit = Objects.requireNonNull(timeUnit, "timeUnit");
    this.stopwatch = Stopwatch.create(timeUnit, Clock.nanoTimeClock());
    initialize();
  }

  public GameScreenContext startStopwatch() {
    stopwatch.start();
    return this;
  }

  public GameScreenContext lapStopwatch() {
    stopwatch.lap();
    return this;
  }

  public GameScreenContext nextFrame(Executable framer) {
    framer.execute();
    return this;
  }

  public GameScreenContext skipFrame(Executable framer) {
    int skips = 0;
    while (excessSleepTime > duration && skips < MAX_SKIP_COUNT) {
      excessSleepTime -= duration;
      framer.execute();
      skips++;
    }
    return this;
  }

  public GameScreenContext calculateSleepTime() {
    sleepTime = duration - stopwatch.lapTime(0, timeUnit) - overSleepTime;
    return this;
  }

  public GameScreenContext sleepOrHandleDelays() {
    return sleepTime > 0L
        ? sleep().lapStopwatch().calculateOverSleepTime() //
        : handleDelays();
  }

  public GameScreenContext sleep() {
    GameUtilities.sleep(sleepTime, timeUnit);
    return this;
  }

  public GameScreenContext calculateOverSleepTime() {
    overSleepTime = stopwatch.lapTime(1, timeUnit) - sleepTime;
    return this;
  }

  public GameScreenContext handleDelays() {
    delayCount++;
    excessSleepTime += Math.abs(sleepTime);
    overSleepTime = 0L;

    if (delayCount >= MAX_DELAY_COUNT) {
      Thread.yield();
      delayCount = 0;
    }

    return this;
  }

  public GameScreenContext initialize() {
    stopwatch.reset();
    sleepTime = 0L;
    overSleepTime = 0L;
    delayCount = 0;
    excessSleepTime = 0L;
    return this;
  }
}
