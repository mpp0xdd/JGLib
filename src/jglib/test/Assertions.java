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

  public static BooleanAssert assertThat(boolean actual) {
    return BooleanAssert.of(actual);
  }

  public static NumberAssert assertThat(Number actual) {
    return NumberAssert.of(actual);
  }

  public static <T> ObjectAssert<T> assertThat(T actual) {
    return ObjectAssert.of(actual);
  }

  public static void assertTrue(boolean condition) {
    assertThat(condition).isTrue();
  }

  public static void assertFalse(boolean condition) {
    assertThat(condition).isFalse();
  }

  public static <T extends Throwable> T assertThrows(Class<T> expected, Test test) {
    try {
      test.execute();
    } catch (Throwable actual) {
      assert expected.equals(actual.getClass()) : AssertionErrorMessage.of(expected, actual);
      return expected.cast(actual);
    }

    fail(AssertionErrorMessage.of(expected, null));
    return null;
  }

  public static void assertDoesNotThrow(Test test) {
    try {
      test.execute();
    } catch (Throwable unexpected) {
      fail(AssertionErrorMessage.of(null, unexpected));
    }
  }

  public static void fail(AssertionErrorMessage<?, ?> message) {
    assert false : message;
  }
}
