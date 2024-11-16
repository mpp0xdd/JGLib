package jglib.component;

import java.awt.Graphics;
import java.util.Objects;
import jglib.util.FrameRate;

/**
 * ゲーム画面の作成を円滑に進めていく為に用意された抽象基底クラスです。
 *
 * @author mpp
 */
public abstract non-sealed class GameScreenEx extends GameScreenBase implements GameScreenRole, Runnable {

  private volatile FrameRate frameRate;
  private volatile OffScreen offScreen;
  private volatile Thread gameLoopThread;

  /**
   * このゲーム画面が達成すべきフレームレートを設定します。
   * 
   * @apiNote
   *   設定は {@link #startGameLoop()} が呼ばれるよりも前に行われる必要があることに注意してください。
   *   設定変更は、実行中のゲームループには影響を及ぼしません。
   * 
   * @param frameRate フレームレート
   */
  public void setFrameRate(FrameRate frameRate) {
    this.frameRate = Objects.requireNonNull(frameRate);
  }

  @Override
  public void startGameLoop() {
    if (Objects.nonNull(gameLoopThread)) {
      throw (new IllegalStateException("実行中のゲームループを開始させることはできません。"));
    }

    createOffScreen();
    startGameLoopThread();
  }

  @Override
  public void stopGameLoop() {
    if (Objects.isNull(gameLoopThread)) {
      throw (new IllegalStateException("開始していないゲームループを停止させることはできません。"));
    }

    gameLoopThread = null;
  }

  @Override
  public void run() {
    final GameScreenContext context = GameScreenContext.nanoTimeContext(frameRate);
    final Thread currentThread = Thread.currentThread();

    context.startStopwatch();
    while (currentThread == gameLoopThread) {
      context
          .nextFrame(this::nextFrame)
          .lapStopwatch()
          .calculateSleepTime()
          .sleepOrHandleDelays()
          .startStopwatch()
          .skipFrame(this::runGameLoop);
    }
  }

  private void createOffScreen() {
    offScreen = OffScreen.create(createImage(getWidth(), getHeight()));
  }

  private void renderOffScreen() {
    offScreen.clear();
    offScreen.render(this::paintGameComponent);
  }

  private void nextFrame() {
    runGameLoop();
    renderOffScreen();
    paintOnScreen();
  }

  private void paintOnScreen() {
    Graphics g = getGraphics();
    try {
      offScreen.draw(g);
      // Toolkit.getDefaultToolkit().sync();
    } finally {
      g.dispose();
    }
  }

  private void startGameLoopThread() {
    gameLoopThread = new Thread(this);
    gameLoopThread.setUncaughtExceptionHandler(new UncaughtGameScreenExceptionHandler());
    gameLoopThread.start();
  }
}
