package jglib.util;

import jglib.base.UncheckedGameException;

public class UncheckedLoadException extends UncheckedGameException {

  public UncheckedLoadException(LoadException cause) {
    super(cause);
  }

  @Override
  public LoadException getCause() {
    return (LoadException) super.getCause();
  }
}
