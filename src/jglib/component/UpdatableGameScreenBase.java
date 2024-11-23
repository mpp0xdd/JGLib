package jglib.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * 更新可能なゲーム画面の抽象基底クラスです。
 *
 * @author mpp
 */
public abstract sealed class UpdatableGameScreenBase extends JPanel
    implements UpdatableGameScreenRole permits GameScreen, GameScreenEx {

  @Override
  public void setScreenSize(int width, int height) {
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    setSize(size);
  }

  /** ゲームループの実際の処理を行います。 */
  protected abstract void runGameLoop();

  /**
   * ゲーム画面の描画処理を行います。
   *
   * @param g Graphicsコンテキスト
   */
  protected abstract void paintGameComponent(Graphics g);

  /**
   * サブスクリーンの描画を行います。
   *
   * @param g サブスクリーンの描画に使用するグラフィックスコンテキスト。
   * @param subscreen サブスクリーン。
   */
  protected void paintSubGameScreen(Graphics g, SubGameScreen subscreen) {
    Image image = createImage(subscreen.width(), subscreen.height());
    subscreen.draw(image.getGraphics());
    g.drawImage(image, subscreen.x(), subscreen.y(), this);
  }
}
