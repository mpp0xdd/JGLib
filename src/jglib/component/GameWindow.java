package jglib.component;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 * ゲームウィンドウを表したクラスです。
 *
 * @author mpp
 */
public class GameWindow {

  private JFrame frame;

  /**
   * 指定されたタイトルで不可視のウィンドウを作成します。
   *
   * @param title フレームのタイトル。
   */
  public GameWindow(String title) {
    this.frame = new JFrame(title);
    this.frame.setLayout(new OverlayLayout(this.frame.getContentPane()));
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setResizable(false);
  }

  /** 不可視のウィンドウを作成します。 */
  public GameWindow() {
    this("Game");
  }

  /**
   * パラメータbの値に応じて，このWindowを表示または非表示にします。
   *
   * @param b trueの場合はWindowを表示する。
   * @see javax.swing.JFrame#setVisible(boolean)
   */
  public void setVisible(boolean b) {
    this.frame.setVisible(b);
  }

  /**
   * このウィンドウ上に表示されているゲーム画面を切り替えます。
   *
   * @param screen 切り替え先のゲーム画面。
   */
  public void switchGameScreen(GameScreen screen) {
    switchScreenImpl(screen);
  }

  /**
   * このウィンドウ上に表示されているゲーム画面を切り替えます。
   *
   * @param screen 切り替え先のゲーム画面。
   */
  public void switchGameScreen(GameScreenEx screen) {
    switchScreenImpl(screen);
  }

  private void switchScreenImpl(JPanel screen) {
    Container contentPane = this.frame.getContentPane();

    contentPane.removeAll();
    contentPane.add(screen);
    this.frame.validate();
    this.frame.pack();
  }
}
