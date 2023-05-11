public class TestExecutor {
  public static void main(String[] args) {
    Test.invokeTestClass(new SpriteSheetTest());

    System.err.println();
    System.err.println("All tests completed successfully.");
  }
}
