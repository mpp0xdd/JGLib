package jglib.test;

class TestExecutor {

  public static void main(String[] args) {
    Test.invokeTestClass(new SpriteSheetTest());
    Test.complete();
  }
}
