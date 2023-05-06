import static java.util.Objects.*;

import java.util.Optional;

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
}
