package jglib.test;

import java.util.concurrent.TimeUnit;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;
import jglib.util.Clock;
import jglib.util.Executable;
import jglib.util.Stopwatch;

@TestClass
class ExecutableTest {

  class ClockStub implements Clock {

    private long time;

    @Override
    public long currentTime() {
      return time;
    }
  }

  @TestMethod
  void test() {
    ClockStub clockStub = new ClockStub();
    Stopwatch stopwatch = Stopwatch.create(TimeUnit.SECONDS, clockStub);

    Executable underTest = () -> clockStub.time = 12L;

    clockStub.time = 5L;

    underTest.execute(stopwatch);

    assert stopwatch.measurementTime() == 7L;
    assert clockStub.time == 12L;
  }
}
