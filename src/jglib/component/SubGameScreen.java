package jglib.component;

import java.awt.Graphics;
import jglib.util.spec.Drawable;
import jglib.util.spec.Rectangular;

/**
 * サブゲーム画面を表すインターフェイスです。
 *
 * @author mpp
 */
public interface SubGameScreen extends Rectangular, Drawable {

  /** @return サブゲーム画面のx座標。 */
  @Override
  int x();

  /** @return サブゲーム画面のy座標。 */
  @Override
  int y();

  /** @return サブゲーム画面の横幅。 */
  @Override
  int width();

  /** @return サブゲーム画面の縦幅。 */
  @Override
  int height();

  /**
   * サブゲーム画面を描画します。
   *
   * @param g サブゲーム画面の描画に使用するグラフィックスコンテキスト。
   */
  @Override
  void draw(Graphics g);
}
