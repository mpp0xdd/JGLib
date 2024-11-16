package jglib.util;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FrameRate {

  public static final FrameRate FPS60 = framesPerSecond(60);

  public static FrameRate framesPerSecond(int fps) {
    return new FrameRate(fps, TimeUnit.SECONDS);
  }

  private static int requireGreaterThanOrEqualToOne(int value) {
    if (value < 1) {
      throw new IllegalArgumentException(String.valueOf(value));
    }
    return value;
  }

  private final int value;
  private final TimeUnit timeUnit;

  private FrameRate(int value, TimeUnit timeUnit) {
    this.value = requireGreaterThanOrEqualToOne(value);
    this.timeUnit = Objects.requireNonNull(timeUnit);
  }

  public Duration toDuration() {
    return Duration.of(1L, timeUnit.toChronoUnit()).dividedBy(value);
  }
}
