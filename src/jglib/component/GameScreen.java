package jglib.component;

import java.awt.Graphics;
import java.util.Objects;
import jglib.util.GameUtilities;

/**
 * ゲーム画面の作成を円滑に進めていく為に用意された抽象基底クラスです。
 *
 * @author mpp
 */
public abstract non-sealed class GameScreen extends UpdatableGameScreenBase implements UpdatableGameScreenRole, Runnable {

  /** このゲーム画面のデフォルトのサイズ(横幅) を表します。 */
  public static final int DEFAULT_WIDTH = 640;

  /** このゲーム画面のデフォルトのサイズ(縦幅) を表します。 */
  public static final int DEFAULT_HEIGHT = 480;

  /** このゲーム画面で実行されるゲームループのデフォルトの実行間隔(ミリ秒) を表します。 */
  public static final long DEFAULT_GAME_LOOP_INTERVAL = 1000L / 50L;

  /** ゲームループを実行するスレッドを表します。この値がnullでないならゲームループが実行されています。 */
  private volatile Thread gameLoopThread = null;

  /** ゲームループの実行間隔(ミリ秒) を表します。 */
  private volatile long gameLoopInterval = DEFAULT_GAME_LOOP_INTERVAL;

  /**
   * 指定されたサイズでゲーム画面を作成します。
   *
   * @param width ゲーム画面の横幅
   * @param height ゲーム画面の縦幅
   */
  public GameScreen(int width, int height) {
    setScreenSize(width, height);
  }

  /** サイズが{@value #DEFAULT_WIDTH} * {@value #DEFAULT_HEIGHT} に設定されたゲーム画面を作成します。 */
  public GameScreen() {
    this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  /**
   * このゲーム画面で実行されるゲームループの実行間隔(ミリ秒) を変更します。<br>
   * デフォルト値は{@value #DEFAULT_GAME_LOOP_INTERVAL} です。
   *
   * @param gameLoopInterval ゲームループの実行間隔(ミリ秒)
   */
  public final void setGameLoopInterval(long gameLoopInterval) {
    this.gameLoopInterval = gameLoopInterval;
  }

  @Override
  public final void startGameLoop() {
    if (Objects.nonNull(gameLoopThread)) {
      throw (new IllegalStateException("実行中のゲームループを開始させることはできません。"));
    }

    gameLoopThread = new Thread(this);
    gameLoopThread.setUncaughtExceptionHandler(new UncaughtGameScreenExceptionHandler());
    gameLoopThread.start();
  }

  @Override
  public final void stopGameLoop() {
    if (Objects.isNull(gameLoopThread)) {
      throw (new IllegalStateException("開始していないゲームループを停止させることはできません。"));
    }

    gameLoopThread = null;
  }

  @Override
  public final void run() {
    Thread thisThread = Thread.currentThread();
    while (thisThread == gameLoopThread) {
      runGameLoop();
      repaint();
      GameUtilities.sleep(gameLoopInterval);
    }
  }

  @Override
  protected final void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.paintGameComponent(g);
  }
}
