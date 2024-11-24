package jglib.util.model;

public enum Keystroke {
  PRESSED,
  NOT_PRESSED,
  ;

  public boolean isPressed() {
    return this.equals(PRESSED);
  }

  public boolean isNotPressed() {
    return this.equals(NOT_PRESSED);
  }
}
