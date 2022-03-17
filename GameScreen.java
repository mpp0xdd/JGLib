import javax.swing.JPanel;
import java.awt.Dimension;


/**
 * ゲーム画面の作成を円滑に進めていく為に用意された抽象基底クラスです。
 *
 * @author mpp0xdd
 */
public abstract class GameScreen extends JPanel implements Runnable {

  /** このゲーム画面のデフォルトの推奨サイズ(横幅) を表します。 */
  public static final int DEFAULT_PREFERRED_WIDTH  = 640;

  /** このゲーム画面のデフォルトの推奨サイズ(縦幅) を表します。 */
  public static final int DEFAULT_PREFERRED_HEIGHT = 480;

  /** ゲームループを実行するスレッドを表します。この値がnullでないならゲームループが実行されています。 */
  private volatile Thread gameLoopThread = null;

  /**
   * 指定された推奨サイズでゲーム画面を作成します。
   * @param preferredWidth 推奨される横幅
   * @param preferredHeight 推奨される縦幅
   */
  public GameScreen(int preferredWidth, int preferredHeight) {
    setPreferredSize(new Dimension(preferredWidth, preferredHeight));
  }

  /**
   * 推奨サイズが{@value #DEFAULT_PREFERRED_WIDTH} * {@value #DEFAULT_PREFERRED_HEIGHT} に設定されたゲーム画面を作成します。
   */
  public GameScreen() {
    this(DEFAULT_PREFERRED_WIDTH, DEFAULT_PREFERRED_HEIGHT);
  }

  /**
   * ゲームループを開始します。<br>
   * このメソッドによって開始されたゲームループを終了したい場合は，{@code stopGameLoop()}を呼び出してください。
   * @throws IllegalStateException ゲームループが既に開始されている場合
   * @see #runGameLoop()
   * @see #stopGameLoop()
   */
  public final void startGameLoop() {
    if(gameLoopThread != null) {
      throw (new IllegalStateException("実行中のゲームループを開始させることはできません。"));
    }

    gameLoopThread = new Thread(this);
    gameLoopThread.start();
  }

  /**
   * ゲームループを停止します。<br>
   * このメソッドによって停止されたゲームループを再び開始したい場合は，{@code startGameLoop()}を呼び出してください。
   * @throws IllegalStateException ゲームループが既に停止している場合
   * @see #runGameLoop()
   * @see #startGameLoop()
   */
  public final void stopGameLoop() {
    if(gameLoopThread == null) {
      throw (new IllegalStateException("開始していないゲームループを停止させることはできません。"));
    }

    gameLoopThread = null;
  }

  /**
   * ゲームループが終了するのを待機します。
   * @throws IllegalStateException ゲームループが既に停止している場合
   * @see #runGameLoop()
   * @see #startGameLoop()
   * @see #stopGameLoop()
   */
  public final void joinGameLoop() {
    if(gameLoopThread == null) {
      throw (new IllegalStateException("開始していないゲームループの終了を待機することはできません。"));
    }

    try {
      gameLoopThread.join();
    }
    catch(InterruptedException ie) {
      ie.printStackTrace();
    }
  }

  /** ゲームループの実際の処理を行います。 */
  protected abstract void runGameLoop();

  @Override
  public final void run() {
    Thread thisThread = Thread.currentThread();
    while(thisThread == gameLoopThread) {
      runGameLoop();
    }
  }
}
