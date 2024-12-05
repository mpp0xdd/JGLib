package jglib.test;

import java.util.Objects;

class ObjectAssert<T> {

  static {
    Assertions.requireEnable();
  }

  public static <T> ObjectAssert<T> of(T actual) {
    return new ObjectAssert<T>(actual);
  }

  private final T actual;

  private ObjectAssert(T actual) {
    this.actual = actual;
  }

  public void isEqualTo(T expected) {
    assert Objects.equals(expected, actual) : AssertionErrorMessage.of(expected, actual);
  }

  public void isNull() {
    assert actual == null;
  }

  public void nonNull() {
    assert actual != null;
  }
}
