package jglib.test;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;
import jglib.util.Stopwatch;

@TestClass
class StopwatchTest {

  class ClockStub implements LongSupplier {

    private long time;

    @Override
    public long getAsLong() {
      return time;
    }
  }

  @TestMethod
  void test() {
    final ClockStub clockStub = new ClockStub();
    final Stopwatch underTest = Stopwatch.create(TimeUnit.SECONDS, clockStub);

    clockStub.time = 0L;
    assert underTest.measurementTime() == 0L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 0L;
    assert underTest.elapsedTime() == 0L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 0L;

    clockStub.time = 1L;
    assert underTest.measurementTime() == 0L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 0L;
    assert underTest.elapsedTime() == 1L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 1000L;

    clockStub.time = 1000L;
    underTest.start();

    clockStub.time = 1017L;
    underTest.stop();

    assert underTest.measurementTime() == 17L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 17000L;
    assert underTest.elapsedTime() == 17L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 17000L;

    clockStub.time = 2000L;
    assert underTest.measurementTime() == 17L;
    assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 17000L;
    assert underTest.elapsedTime() == 1000L;
    assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 1000000L;
  }

  @TestMethod
  void testIllegalOperation() {
    { // stopの前に結果を取得しようとした場合
      ClockStub clockStub = new ClockStub();
      Stopwatch underTest = Stopwatch.create(TimeUnit.SECONDS, clockStub);

      clockStub.time = 12L;
      underTest.start();
      Test.assertThrows(IllegalStateException.class, () -> underTest.measurementTime());
      Test.assertThrows(
          IllegalStateException.class, () -> underTest.measurementTime(TimeUnit.MILLISECONDS));
      assert underTest.elapsedTime() == 0L;
      assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 0L;
    }

    { // stop --> start の順に呼ばれた場合
      ClockStub clockStub = new ClockStub();
      Stopwatch underTest = Stopwatch.create(TimeUnit.SECONDS, clockStub);

      clockStub.time = 12L;
      underTest.stop();
      assert underTest.measurementTime() == 12L;
      assert underTest.measurementTime(TimeUnit.MILLISECONDS) == 12000L;
      assert underTest.elapsedTime() == 12L;
      assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 12000L;

      clockStub.time = 13L;
      underTest.start();
      Test.assertThrows(IllegalStateException.class, () -> underTest.measurementTime());
      Test.assertThrows(
          IllegalStateException.class, () -> underTest.measurementTime(TimeUnit.MILLISECONDS));
      assert underTest.elapsedTime() == 0L;
      assert underTest.elapsedTime(TimeUnit.MILLISECONDS) == 0L;
    }
  }
}
