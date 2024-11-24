package jglib.component;

import java.awt.Graphics;
import jglib.util.spec.Drawable;

/**
 * サブゲーム画面を表すインターフェイスです。
 *
 * @author mpp
 */
public interface SubGameScreen extends Drawable {

  /** @return サブゲーム画面のx座標。 */
  int x();

  /** @return サブゲーム画面のy座標。 */
  int y();

  /** @return サブゲーム画面の横幅。 */
  int width();

  /** @return サブゲーム画面の縦幅。 */
  int height();

  /**
   * サブゲーム画面を描画します。
   *
   * @param g サブゲーム画面の描画に使用するグラフィックスコンテキスト。
   */
  @Override
  void draw(Graphics g);
}
