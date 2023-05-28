package jglib.tests;

public class TestExecutor {
  public static void main(String[] args) {
    Test.invokeTestClass(new SpriteSheetTest());

    System.err.println();
    assert false : "All tests completed successfully.";
  }
}
