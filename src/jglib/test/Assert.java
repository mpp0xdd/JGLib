package jglib.test;

import java.util.Objects;

class Assert<T> {

  static {
    Assertions.requireEnable();
  }

  public static <T> Assert<T> of(T actual) {
    return new Assert<T>(actual);
  }

  private final T actual;

  private Assert(T actual) {
    this.actual = actual;
  }

  public void isEqualTo(T expected) {
    assert Objects.equals(expected, actual);
  }
}
