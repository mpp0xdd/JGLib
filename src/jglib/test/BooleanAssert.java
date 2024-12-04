package jglib.test;

class BooleanAssert {

  static {
    Assertions.requireEnable();
  }

  public static BooleanAssert of(boolean actual) {
    return new BooleanAssert(actual);
  }

  private final boolean actual;

  private BooleanAssert(boolean actual) {
    this.actual = actual;
  }

  public void isTrue() {
    assert actual;
  }

  public void isFalse() {
    assert !actual;
  }
}
