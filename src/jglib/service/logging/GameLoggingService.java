package jglib.service.logging;

import java.util.Objects;

public final class GameLoggingService {

  static {
    initialize(new GameLoggerImpl());
  }

  private static GameLogger logger;

  private GameLoggingService() {}

  public static GameLogger getLogger() {
    return Objects.requireNonNull(logger);
  }

  public static void initialize(GameLogger logger) {
    GameLoggingService.logger = Objects.requireNonNull(logger);
  }
}
