package jglib.test;

import java.util.Optional;
import jglib.base.Game;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;

@TestClass
class GameTest {

  private static int launchCount = 0;

  public static class NormalGame extends Game {

    @Override
    protected void start() {
      launchCount++;
    }
  }

  @TestMethod
  void test() {
    launchCount = 0;

    Optional<NormalGame> game = Game.launch(NormalGame.class);
    assert launchCount == 1 : "launchCount should be 1 but was " + launchCount;
    assert game.isPresent();
  }
}
