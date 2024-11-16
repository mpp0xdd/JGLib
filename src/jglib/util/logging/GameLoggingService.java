package jglib.util.logging;

import java.util.Objects;

public final class GameLoggingService {

  private static GameLogger logger = null;

  private GameLoggingService() {}

  public static GameLogger getLogger() {
    return Objects.requireNonNull(logger);
  }

  public static void initialize(GameLogger logger) {
    GameLoggingService.logger = Objects.requireNonNull(logger);
  }

  public static void initialize() {
    if (Objects.isNull(logger)) {
      initialize(new GameLoggerImpl());
    }
  }
}
