package jglib.test;

import static jglib.test.Tests.getTestMethodsOrElseThrow;
import static jglib.test.Tests.requireTestClass;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class TestExecutor {

  private final List<Class<?>> testClasses;

  public TestExecutor() {
    testClasses = new ArrayList<>();
  }

  public void register(Class<?> clazz) {
    testClasses.add(requireTestClass(clazz));
  }

  public void execute() {
    Assertions.requireEnable();
    testClasses.forEach(this::instantiateAndTest);
    done();
  }

  private void instantiateAndTest(Class<?> clazz) {
    List<Method> testMethods = getTestMethodsOrElseThrow(clazz);

    System.err.printf("> Start %s%n", clazz.getSimpleName());
    invokeTestMethods(clazz, testMethods);
    System.err.printf("> %s completed successfully%n", clazz.getSimpleName());
    System.err.println();
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
