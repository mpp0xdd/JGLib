package jglib.test;

public class TestExecutor {

  private static boolean complete() {
    System.err.println();
    System.err.println("All tests completed successfully.");
    return true;
  }

  public static void main(String[] args) {
    Test.invokeTestClass(new SpriteSheetTest());
    assert complete();
  }
}
