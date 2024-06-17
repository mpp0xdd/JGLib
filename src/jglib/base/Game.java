package jglib.base;

public abstract class Game {

  public static void launch(Class<? extends Game> gameClass) {
    try {
      Game game = gameClass.getDeclaredConstructor().newInstance();
      game.start();
    } catch (Exception e) {
      GameError error = new GameError(e);
      GameLogger.getLogger().error(error);
      System.exit(1);
    }
  }

  protected Game() {
    Thread.currentThread().setUncaughtExceptionHandler(new UncaughtGameExceptionHandler());
  }

  protected abstract void start();
}
