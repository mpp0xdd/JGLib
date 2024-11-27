package jglib.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@FunctionalInterface
interface Test {

  void execute() throws Throwable;

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestClass {}

  @Target({ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface TestMethod {}
}
