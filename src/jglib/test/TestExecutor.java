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

  private <T> void instantiateAndTest(Class<T> clazz) {
    if (isNotTestClass(clazz)) {
      throw new RuntimeException("Not a test class: " + clazz);
    }

    T instance = newInstance(clazz);

    final List<Method> testMethods =
        Stream.of(clazz.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(TestMethod.class))
            .collect(Collectors.toUnmodifiableList());

    if (testMethods.isEmpty()) {
      throw new RuntimeException("Test method not found: " + clazz);
    }

    try {
      System.err.printf("> Start %s%n", clazz.getSimpleName());
      for (Method testMethod : testMethods) {
        System.err.printf(String.format(">> %s()", testMethod.getName()));
        testMethod.invoke(instance);
        System.err.println(" completed successfully.");
      }
      System.err.printf("> %s completed successfully.%n", clazz.getSimpleName());
      System.err.println();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isNotTestClass(Class<?> clazz) {
    return !clazz.isAnnotationPresent(TestClass.class);
  }

  private <T> T newInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
