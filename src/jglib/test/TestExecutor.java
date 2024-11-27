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

  private void instantiateAndTest(Class<?> clazz) {
    requireTestClass(clazz);

    List<Method> testMethods = getTestMethods(clazz);

    if (testMethods.isEmpty()) {
      throw new RuntimeException("Test method not found: " + clazz);
    }

    System.err.printf("> Start %s%n", clazz.getSimpleName());
    invokeTestMethods(clazz, testMethods);
    System.err.printf("> %s completed successfully%n", clazz.getSimpleName());
    System.err.println();
  }

  private boolean isNotTestClass(Class<?> clazz) {
    return !clazz.isAnnotationPresent(TestClass.class);
  }

  private void requireTestClass(Class<?> clazz) {
    if (isNotTestClass(clazz)) {
      throw new AssertionError("Not a test class: " + clazz);
    }
  }

  private boolean isTestMethod(Method method) {
    return method.isAnnotationPresent(TestMethod.class);
  }

  private List<Method> getTestMethods(Class<?> testClass) {
    return Stream.of(testClass.getDeclaredMethods())
        .filter(this::isTestMethod)
        .collect(Collectors.toUnmodifiableList());
  }

  private void invokeTestMethods(Class<?> testClass, List<Method> testMethods) {
    try {
      Object instance = testClass.getDeclaredConstructor().newInstance();
      for (Method testMethod : testMethods) {
        testMethod.invoke(instance);
        System.err.printf(">> %s() completed successfully%n", testMethod.getName());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void done() {
    System.err.println("All tests completed successfully");
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
