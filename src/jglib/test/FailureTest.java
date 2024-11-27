package jglib.test;

import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;

@TestClass
class FailureTest {

  @TestMethod
  void test() {
    assert false : "Test failed";
  }
}
