package jglib.core;

public class UncheckedGameException extends RuntimeException
    implements GameThrowable<UncheckedGameException> {

  public UncheckedGameException() {}

  public UncheckedGameException(String message) {
    super(message);
  }

  public UncheckedGameException(Throwable cause) {
    super(cause);
  }

  public UncheckedGameException(String message, Throwable cause) {
    super(message, cause);
  }

  public UncheckedGameException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
