package jglib.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
}
