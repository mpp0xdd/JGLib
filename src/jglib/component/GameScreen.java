package jglib.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import javax.swing.JPanel;
import jglib.util.GameUtilities;

/**
 * ゲーム画面の作成を円滑に進めていく為に用意された抽象基底クラスです。
 *
 * @author mpp
 */
public abstract class GameScreen extends JPanel implements Runnable {

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
   * ゲーム画面のサイズを変更します。
   *
   * @param width ゲーム画面の横幅
   * @param height ゲーム画面の縦幅
   */
  public final void setScreenSize(int width, int height) {
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    setSize(size);
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

  /**
   * ゲームループを開始します。<br>
   * このメソッドによって開始されたゲームループを終了したい場合は，{@code stopGameLoop()}を呼び出してください。
   *
   * @throws IllegalStateException ゲームループが既に開始されている場合
   * @see #runGameLoop()
   * @see #stopGameLoop()
   */
  public final void startGameLoop() {
    if (Objects.nonNull(gameLoopThread)) {
      throw (new IllegalStateException("実行中のゲームループを開始させることはできません。"));
    }

    gameLoopThread = new Thread(this);
    gameLoopThread.start();
  }

  /**
   * ゲームループを停止します。<br>
   * このメソッドによって停止されたゲームループを再び開始したい場合は，{@code startGameLoop()}を呼び出してください。
   *
   * @throws IllegalStateException ゲームループが既に停止している場合
   * @see #runGameLoop()
   * @see #startGameLoop()
   */
  public final void stopGameLoop() {
    if (Objects.isNull(gameLoopThread)) {
      throw (new IllegalStateException("開始していないゲームループを停止させることはできません。"));
    }

    gameLoopThread = null;
  }

  /**
   * ゲームループが終了するのを待機します。
   *
   * @throws IllegalStateException ゲームループが既に停止している場合
   * @see #runGameLoop()
   * @see #startGameLoop()
   * @see #stopGameLoop()
   */
  public final void joinGameLoop() {
    if (Objects.isNull(gameLoopThread)) {
      throw (new IllegalStateException("開始していないゲームループの終了を待機することはできません。"));
    }

    try {
      gameLoopThread.join();
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
  }

  /** ゲームループの実際の処理を行います。 */
  protected abstract void runGameLoop();

  @Override
  public final void run() {
    Thread thisThread = Thread.currentThread();
    while (thisThread == gameLoopThread) {
      runGameLoop();
      GameUtilities.sleep(gameLoopInterval);
    }
  }

  /**
   * サブスクリーンの描画を行います。
   *
   * @param g サブスクリーンの描画に使用するグラフィックスコンテキスト。
   * @param subscreen サブスクリーン。
   */
  protected final void paintSubGameScreen(Graphics g, SubGameScreen subscreen) {
    Image image = createImage(subscreen.width(), subscreen.height());
    subscreen.draw(image.getGraphics());
    g.drawImage(image, subscreen.x(), subscreen.y(), this);
  }
}
