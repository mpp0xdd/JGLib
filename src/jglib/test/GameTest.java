package jglib.test;

import java.util.Optional;
import jglib.base.Game;
import jglib.base.GameEnvironment;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;
import jglib.util.logging.GameLoggingService;

@TestClass
class GameTest {

  static {
    GameEnvironment.initialize();
  }

  private static int launchCount = 0;

  public static class NormalGame extends Game {

    @Override
    protected void start() {
      launchCount++;
    }
  }

  public static class LaunchFailGame extends Game {

    @Override
    protected void start() {
      throw new RuntimeException("Failed to start game!");
    }
  }

  @TestMethod
  void test() {
    launchCount = 0;

    Optional<NormalGame> game = Game.launch(NormalGame.class);
    assert launchCount == 1 : "launchCount should be 1 but was " + launchCount;
    assert game.isPresent();
  }

  @TestMethod
  void testLaunchFail() {
    GameLoggerStub loggerStub = GameLoggerStub.create();
    GameLoggingService.initialize(loggerStub);

    Optional<LaunchFailGame> game = Game.launch(LaunchFailGame.class);

    assert game.isEmpty();
    assert loggerStub.getError().size() == 1;
    assert loggerStub.getWarning().isEmpty();
    assert loggerStub
        .getErrorCause(RuntimeException.class, 0)
        .getMessage()
        .equals("Failed to start game!");
  }
}
