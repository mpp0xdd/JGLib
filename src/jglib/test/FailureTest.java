package jglib.test;

import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;

@TestClass
class FailureTest {

  @TestMethod
  void test() {
    assert false : "Test failed";
  }
}
