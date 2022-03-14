import javax.swing.JFrame;
import javax.swing.OverlayLayout;
import java.awt.Container;


/**
 * ゲームウィンドウの作成を円滑に進めていく為に用意された抽象基底クラスです。
 * <h2>注意点:</h2>
 * このクラスを継承したウィンドウのレイアウト・マネージャの変更は行わないようにしてください。<br>
 * レイアウト・マネージャの変更が行われた状態で，このクラスで定義されたメソッドを呼び出した場合の動作は未定義です。
 *
 * @author mpp0xdd
 */
public abstract class GameWindow extends JFrame {

  /**
   * 指定されたタイトルで不可視のウィンドウを作成します。
   * @param title フレームのタイトル。
   */
  public GameWindow(String title) {
    super(title);
    setLayout(new OverlayLayout(getContentPane()));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * 不可視のウィンドウを作成します。
   */
  public GameWindow() {
    this("Game");
  }

  /**
   * このウィンドウ上に表示されているゲーム画面を切り替えます。
   * @param screen 切り替え先のゲーム画面。
   */
  public void switchGameScreen(GameScreen screen) {
    Container contentPane = getContentPane();

    contentPane.removeAll();
    contentPane.add(screen);
    validate();
  }
}
