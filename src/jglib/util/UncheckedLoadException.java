package jglib.util;

import static java.util.Objects.requireNonNull;
import jglib.base.UncheckedGameException;

public class UncheckedLoadException extends UncheckedGameException {

  public UncheckedLoadException(LoadException cause) {
    super(requireNonNull(cause));
  }

  @Override
  public LoadException getCause() {
    return (LoadException) super.getCause();
  }
}
