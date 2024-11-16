package jglib.test;

import java.util.concurrent.TimeUnit;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;
import jglib.util.Executable;
import jglib.util.time.Stopwatch;

@TestClass
class ExecutableTest {

  @TestMethod
  void test() {
    ClockStub clockStub = ClockStub.newClock();
    Stopwatch stopwatch = Stopwatch.create(TimeUnit.SECONDS, clockStub);

    Executable underTest = () -> clockStub.setCurrentTime(12L);

    clockStub.setCurrentTime(5L);

    underTest.execute(stopwatch);

    assert stopwatch.measurementTime() == 7L;
    assert clockStub.currentTime() == 12L;
  }
}
