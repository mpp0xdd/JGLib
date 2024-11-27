package jglib.test;

import jglib.test.Tests.Test;

class Assertions {

  static {
    requireEnable();
  }

  public static void requireEnable() {
    boolean enableAssertions = false;
    assert enableAssertions = true;
    if (!enableAssertions) {
      throw new AssertionError("Assertions are not enabled");
    }
  }

  public static void assertTrue(boolean condition) {
    assert condition;
  }

  public static void assertFalse(boolean condition) {
    assert !condition;
  }

  public static <T extends Throwable> T assertThrows(Class<T> expected, Test test) {
    try {
      test.execute();
      assert false;
      return null;
    } catch (Throwable actual) {
      assert expected.equals(actual.getClass()) : actual;
      return expected.cast(actual);
    }
  }

  public static void assertDoesNotThrow(Test test) {
    try {
      test.execute();
    } catch (Throwable unexpected) {
      assert false : unexpected;
    }
  }
}
