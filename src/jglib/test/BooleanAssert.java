package jglib.test;

class BooleanAssert {

  static {
    Assertions.requireEnable();
  }

  private static final BooleanAssert TRUE = of(true);
  private static final BooleanAssert FALSE = of(false);

  public static BooleanAssert of(boolean actual) {
    return actual ? TRUE : FALSE;
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
