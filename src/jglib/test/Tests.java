package jglib.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Tests {

  @FunctionalInterface
  interface Test {
    void execute() throws Throwable;
  }

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestClass {}

  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestMethod {}

  public static boolean isTestClass(Class<?> clazz) {
    return clazz.isAnnotationPresent(TestClass.class);
  }

  public static boolean isNotTestClass(Class<?> clazz) {
    return !isTestClass(clazz);
  }

  public static <T> Class<T> requireTestClass(Class<T> clazz) {
    if (isTestClass(clazz)) {
      return clazz;
    }

    throw new AssertionError("Not a test class: " + clazz);
  }

  public static boolean isTestMethod(Method method) {
    return method.isAnnotationPresent(TestMethod.class);
  }

  public static boolean isNotTestMethod(Method method) {
    return !isTestMethod(method);
  }

  public static Stream<Method> testMethodsStream(Class<?> testClass) {
    return Stream.of(testClass.getDeclaredMethods()).filter(Tests::isTestMethod);
  }

  public static List<Method> testMethods(Class<?> testClass) {
    return testMethodsStream(testClass).collect(Collectors.toUnmodifiableList());
  }

  public static List<Method> testMethodsOrElseThrow(Class<?> testClass) {
    List<Method> testMethods = testMethods(testClass);

    if (testMethods.isEmpty()) {
      throw new AssertionError("Test method not found: " + testClass);
    }

    return testMethods;
  }
}
