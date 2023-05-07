import static java.util.Objects.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface TestClass {}

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface TestMethod {}

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
    System.err.println();
    return Optional.ofNullable(exception);
  }

  static void invokeTestClass(Object object) {
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
      System.err.printf("> Start %s.%n", object.getClass().getSimpleName());
      System.err.println("-----------------------------------------------------------------");
      for (Method method : testMethods) {
        System.err.printf(String.format(">> Start %s()%n", method.getName()));
        method.invoke(object);
        System.err.printf(String.format(">> %s() completed successfully.%n", method.getName()));
        System.err.println("---------------------------------------------------");
      }
      System.err.printf("> %s completed successfully.%n", object.getClass().getSimpleName());
      System.err.println("-----------------------------------------------------------------");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
