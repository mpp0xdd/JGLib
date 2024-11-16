package jglib.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import javax.swing.JPanel;
import jglib.util.FrameRate;

public abstract class GameScreenEx extends JPanel implements Runnable {

  private volatile FrameRate frameRate;
  private volatile OffScreen offScreen;
  private volatile Thread gameLoopThread;

  public void setFrameRate(FrameRate frameRate) {
    this.frameRate = Objects.requireNonNull(frameRate);
  }

  public void setScreenSize(int width, int height) {
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    setSize(size);
  }

  public void startGameLoop() {
    if (Objects.nonNull(gameLoopThread)) {
      throw (new IllegalStateException("実行中のゲームループを開始させることはできません。"));
    }

    createOffScreen();
    startGameLoopThread();
  }

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

  protected abstract void runGameLoop();

  protected abstract void paintGameComponent(Graphics g);

  protected void paintSubGameScreen(Graphics g, SubGameScreen subscreen) {
    Image image = createImage(subscreen.width(), subscreen.height());
    subscreen.draw(image.getGraphics());
    g.drawImage(image, subscreen.x(), subscreen.y(), this);
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
