package jglib.core;

public class GameException extends Exception implements GameThrowable<GameException> {

  public GameException() {}

  public GameException(String message) {
    super(message);
  }

  public GameException(Throwable cause) {
    super(cause);
  }

  public GameException(String message, Throwable cause) {
    super(message, cause);
  }

  public GameException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
