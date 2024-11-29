package jglib.test;

import static jglib.test.Assertions.assertThat;
import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;
import jglib.util.model.FrameRate;

@TestClass
class FrameRateTest {

  @TestMethod
  void test() {
    assertThat(FrameRate.framesPerSecond(40).toDuration().toMillis()).isEqualTo(25L);
    assertThat(FrameRate.framesPerSecond(50).toDuration().toMillis()).isEqualTo(20L);
    assertThat(FrameRate.framesPerSecond(60).toDuration().toMillis()).isEqualTo(16L);
    assertThat(FrameRate.framesPerSecond(165).toDuration().toMillis()).isEqualTo(6L);
  }
}
