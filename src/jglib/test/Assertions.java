package jglib.test;

class Assertions {

  public static void assertTrue(boolean condition) {
    assert condition;
  }

  public static void assertFalse(boolean condition) {
    assert !condition;
  }

  public static <T extends Throwable> T assertThrows(Class<T> expected, Test test) {
    try {
      test.test();
      assert false;
      return null;
    } catch (Throwable actual) {
      assert expected.equals(actual.getClass()) : actual;
      return expected.cast(actual);
    }
  }

  public static void assertDoesNotThrow(Test test) {
    try {
      test.test();
    } catch (Throwable unexpected) {
      assert false : unexpected;
    }
  }
}
