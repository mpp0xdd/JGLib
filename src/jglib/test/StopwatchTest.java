package jglib.test;

import java.util.concurrent.TimeUnit;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;
import jglib.util.Stopwatch;

@TestClass
class StopwatchTest {

  @TestMethod
  void test() {
    final ClockStub clockStub = ClockStub.newClock();
    final Stopwatch underTest = Stopwatch.create(TimeUnit.SECONDS, clockStub);

    clockStub.setCurrentTime(0L);
    assert underTest.measurementTime() == 0L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 0L;
    assert underTest.elapsedTime() == 0L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 0L;

    clockStub.setCurrentTime(1L);
    assert underTest.measurementTime() == 0L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 0L;
    assert underTest.elapsedTime() == 1L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 1000L;

    clockStub.setCurrentTime(1000L);
    underTest.start();

    clockStub.setCurrentTime(1017L);
    underTest.stop();

    assert underTest.measurementTime() == 17L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 17000L;
    assert underTest.elapsedTime() == 17L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 17000L;

    clockStub.setCurrentTime(2000L);
    assert underTest.measurementTime() == 17L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 17000L;
    assert underTest.elapsedTime() == 1000L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 1000000L;

    underTest.reset();
    assert underTest.measurementTime() == 0L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 0L;
    assert underTest.elapsedTime() == 2000L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 2000000L;
  }

  @TestMethod
  void testIllegalOperation() {
    { // stopの前に結果を取得しようとした場合
      ClockStub clockStub = ClockStub.newClock();
      Stopwatch underTest = Stopwatch.create(TimeUnit.SECONDS, clockStub);

      clockStub.setCurrentTime(12L);
      underTest.start();
      Test.assertThrows(IllegalStateException.class, () -> underTest.measurementTime());
      Test.assertThrows(
          IllegalStateException.class, () -> underTest.measurementTime(TimeUnit.MILLISECONDS));
      assert underTest.elapsedTime() == 0L;
      assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 0L;
    }

    { // stop --> start の順に呼ばれた場合
      ClockStub clockStub = ClockStub.newClock();
      Stopwatch underTest = Stopwatch.create(TimeUnit.SECONDS, clockStub);

      clockStub.setCurrentTime(12L);
      underTest.stop();
      assert underTest.measurementTime() == 12L;
      assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 12000L;
      assert underTest.elapsedTime() == 12L;
      assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 12000L;

      clockStub.setCurrentTime(13L);
      underTest.start();
      Test.assertThrows(IllegalStateException.class, () -> underTest.measurementTime());
      Test.assertThrows(
          IllegalStateException.class, () -> underTest.measurementTime(TimeUnit.MILLISECONDS));
      assert underTest.elapsedTime() == 0L;
      assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 0L;
    }
  }
}
