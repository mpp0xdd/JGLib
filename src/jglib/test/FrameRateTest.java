package jglib.test;

import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;
import jglib.util.model.FrameRate;

@TestClass
class FrameRateTest {

  @TestMethod
  void test() {
    assert FrameRate.framesPerSecond(40).toDuration().toMillis() == 25L;
    assert FrameRate.framesPerSecond(50).toDuration().toMillis() == 20L;
    assert FrameRate.framesPerSecond(60).toDuration().toMillis() == 16L;
    assert FrameRate.framesPerSecond(165).toDuration().toMillis() == 6L;
  }
}
