package jglib.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;

class TestExecutor {

  private final List<Class<?>> testClasses;

  public TestExecutor() {
    testClasses = new ArrayList<>();
  }

  public void register(Class<?> testClass) {
    testClasses.add(testClass);
  }

  public void execute() {
    Assertions.requireEnable();
    testClasses.forEach(this::instantiateAndTest);
    done();
  }

  private <T> void instantiateAndTest(Class<T> testClass) {
    if (isNotTestClass(testClass)) {
      throw new RuntimeException("Not a test class: " + testClass);
    }

    T testInstance;
    try {
      testInstance = testClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    final List<Method> testMethods =
        Stream.of(testClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(TestMethod.class))
            .collect(Collectors.toUnmodifiableList());

    if (testMethods.isEmpty()) {
      throw new RuntimeException("Test method not found: " + testClass);
    }

    try {
      System.err.printf("> Start %s%n", testClass.getSimpleName());
      for (Method testMethod : testMethods) {
        System.err.printf(String.format(">> %s()", testMethod.getName()));
        testMethod.invoke(testInstance);
        System.err.println(" completed successfully.");
      }
      System.err.printf("> %s completed successfully.%n", testClass.getSimpleName());
      System.err.println();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isNotTestClass(Class<?> clazz) {
    return !clazz.isAnnotationPresent(TestClass.class);
  }

  private void done() {
    System.err.println("All tests completed successfully.");
  }

  public static void main(String[] args) {
    TestExecutor executor = new TestExecutor();

    executor.register(GameTest.class);
    executor.register(SpriteSheetTest.class);
    executor.register(StringDrawerTest.class);
    executor.register(StopwatchTest.class);
    executor.register(FrameRateTest.class);
    executor.register(KeyboardTest.class);

    executor.execute();
  }
}
