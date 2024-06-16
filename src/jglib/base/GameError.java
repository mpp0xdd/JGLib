package jglib.base;

public class GameError extends Error implements GameThrowable<GameError> {

  public GameError() {
    super();
  }

  public GameError(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public GameError(String message, Throwable cause) {
    super(message, cause);
  }

  public GameError(String message) {
    super(message);
  }

  public GameError(Throwable cause) {
    super(cause);
  }
}
