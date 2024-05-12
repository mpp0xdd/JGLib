package jglib.test;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
interface Test {

  void test() throws Exception;

  public static Exception assertThrows(Class<? extends Exception> expected, Test test) {
    Exception exception;
    Class<? extends Exception> actual;
    try {
      test.test();
      exception = null;
      actual = null;
    } catch (Exception e) {
      exception = e;
      actual = e.getClass();
    }
    assert nonNull(expected)
        ? nonNull(exception) && expected.equals(actual)
        : isNull(exception) && isNull(actual);
    return exception;
  }

  public static void assertDoesNotThrow(Test test) {
    assertThrows(null, test);
  }

  public static void invokeTestClass(Object object) {
    requireNonNull(object);
    if (!object.getClass().isAnnotationPresent(TestClass.class)) {
      System.err.println("[ERROR] Not a test class, so cannot be invoked: " + object.getClass());
      System.exit(1);
    }

    Method[] declaredMethods = requireNonNull(object.getClass().getDeclaredMethods());
    List<Method> testMethods =
        Stream.of(declaredMethods)
            .filter(method -> method.isAnnotationPresent(TestMethod.class))
            .collect(Collectors.toList());

    if (testMethods.isEmpty()) {
      System.err.println("[ERROR] Test method not found: " + object.getClass());
      System.exit(1);
    }

    try {
      System.err.printf("> Start %s%n", object.getClass().getSimpleName());
      for (Method testMethod : testMethods) {
        System.err.println();
        System.err.printf(String.format(">> Start %s()%n", testMethod.getName()));
        testMethod.invoke(object);
        System.err.printf(String.format(">> %s() completed successfully.%n", testMethod.getName()));
      }
      System.err.println();
      System.err.printf("> %s completed successfully.%n", object.getClass().getSimpleName());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestClass {}

  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestMethod {}
}
