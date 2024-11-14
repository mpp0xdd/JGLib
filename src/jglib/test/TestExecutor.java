package jglib.test;

import java.util.ArrayList;
import java.util.List;

class TestExecutor {

  private final List<Object> testClasses;

  public TestExecutor() {
    testClasses = new ArrayList<>();
  }

  public void register(Object testClass) {
    testClasses.add(testClass);
  }

  public void execute() {
    checkEnableAssertions();
    testClasses.forEach(Test::invokeTestClass);
    done();
  }

  private void checkEnableAssertions() {
    boolean enableAssertions = false;
    assert enableAssertions = true;
    if (!enableAssertions) {
      throw new AssertionError("Tests cannot be run because assertions are not enabled");
    }
  }

  private void done() {
    System.err.println();
    System.err.println("All tests completed successfully.");
  }

  public static void main(String[] args) {
    TestExecutor executor = new TestExecutor();

    executor.register(new GameTest());
    executor.register(new SpriteSheetTest());
    executor.register(new StringDrawerTest());
    executor.register(new StopwatchTest());
    executor.register(new FrameRateTest());
    executor.register(new ExecutableTest());

    executor.execute();
  }
}
