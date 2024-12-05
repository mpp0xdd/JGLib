package jglib.test;

class BooleanAssert {

  static {
    Assertions.requireEnable();
  }

  private static final BooleanAssert TRUE = new BooleanAssert(true);
  private static final BooleanAssert FALSE = new BooleanAssert(false);

  public static BooleanAssert of(boolean actual) {
    return actual ? TRUE : FALSE;
  }

  private final boolean actual;

  private BooleanAssert(boolean actual) {
    this.actual = actual;
  }

  public void isTrue() {
    assert actual : AssertionErrorMessage.of(true, actual);
  }

  public void isFalse() {
    assert !actual : AssertionErrorMessage.of(false, actual);
  }
}
