package jglib.base;

import java.util.Optional;
import jglib.util.logging.GameLoggingService;

public abstract class Game {

  static {
    GameEnvironment.initialize();
  }

  public static <T extends Game> Optional<T> launch(Class<T> gameClass) {
    T game;

    try {
      game = gameClass.getDeclaredConstructor().newInstance();
      game.start();
    } catch (Exception e) {
      GameError error = new GameError(e);
      GameLoggingService.getLogger().error(error);
      game = null;
    }

    return Optional.ofNullable(game);
  }

  protected Game() {
    Thread.currentThread().setUncaughtExceptionHandler(new UncaughtGameExceptionHandler());
  }

  protected abstract void start();
}
