package jglib.test;

import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;

@TestClass
class ManualTest {

  @TestMethod
  void test() {
    assert false : "Test failed";
  }
}
