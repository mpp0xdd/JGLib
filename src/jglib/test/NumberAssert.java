package jglib.test;

import java.util.Objects;

class NumberAssert {

  static {
    Assertions.requireEnable();
  }

  public static NumberAssert of(Number actual) {
    return new NumberAssert(actual);
  }

  private final Number actual;

  private NumberAssert(Number actual) {
    this.actual = Objects.requireNonNull(actual);
  }

  public void isEqualTo(Number expected) {
    assert expected.equals(actual);
  }

  public void isZero() {
    assert actual.longValue() == 0L && actual.doubleValue() == 0.0d;
  }
}
