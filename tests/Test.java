import static java.util.Objects.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface Test {
  void test() throws Exception;

  static Optional<Exception> assertThrows(
      Class<? extends Exception> expected, String code, Test test) {
    Exception exception;
    Class<? extends Exception> actual;
    String message;
    try {
      test.test();
      exception = null;
      actual = null;
      message = null;
    } catch (Exception e) {
      exception = e;
      actual = e.getClass();
      message = e.getMessage();
    }

    System.err.println(code);
    assert nonNull(expected)
        ? nonNull(exception) && expected.equals(actual)
        : isNull(exception) && isNull(actual);
    System.err.printf(" => Exception %s thrown as expected.\n", expected);
    System.err.println(
        message != null && !message.isEmpty()
            ? message.replaceAll("(?m)^", "    ")
            : "    No message.");
    System.err.println("---------------------------------");
    return Optional.ofNullable(exception);
  }

  static void invokeTestClass(Object object) {
    requireNonNull(object);
    if (!object.getClass().isAnnotationPresent(TestClass.class)) {
      System.err.println("Not a test class, so cannot be invoked.");
      System.err.println(" => " + object);
      System.exit(1);
    }

    Method[] declaredMethods = requireNonNull(object.getClass().getDeclaredMethods());
    List<Method> testMethods =
        Stream.of(declaredMethods)
            .filter(method -> method.isAnnotationPresent(TestMethod.class))
            .collect(Collectors.toList());

    if (testMethods.isEmpty()) {
      System.err.println("Test method not found");
      System.err.println(" => " + object);
      System.exit(1);
    }

    try {
      System.err.printf("> Start %s.%n", object.getClass().getSimpleName());
      for (Method method : testMethods) method.invoke(object);
      System.err.printf("> %s completed successfully.%n", object.getClass().getSimpleName());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
