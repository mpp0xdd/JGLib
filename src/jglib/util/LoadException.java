package jglib.util;

import jglib.base.GameException;

public class LoadException extends GameException {

  private static String message(Object name) {
    return String.format("Load failed: %s", name);
  }

  private final Object name;

  public LoadException(Object name) {
    super(message(name));
    this.name = name;
  }

  public LoadException(Object name, Throwable cause) {
    super(message(name), cause);
    this.name = name;
  }

  public Object getName() {
    return name;
  }
}
