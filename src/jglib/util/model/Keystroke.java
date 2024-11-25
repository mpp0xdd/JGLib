package jglib.util.model;

public enum Keystroke {
  PRESSED,
  RELEASED,
  ;

  public boolean isPressed() {
    return this.equals(PRESSED);
  }

  public boolean isReleased() {
    return this.equals(RELEASED);
  }
}
