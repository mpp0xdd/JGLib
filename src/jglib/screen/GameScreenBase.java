package jglib.screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * 全てのゲーム画面の抽象基底クラスです。
 *
 * @author mpp
 */
abstract sealed class GameScreenBase extends JPanel implements GameScreenRole
    permits SimpleGameScreen, UpdatableGameScreenBase {

  @Override
  public void setScreenSize(int width, int height) {
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    setSize(size);
  }

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
