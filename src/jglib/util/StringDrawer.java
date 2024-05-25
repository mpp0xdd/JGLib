package jglib.util;

import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * StringDrawer列挙型は，文字列のドロワーを表します。
 *
 * @author mpp
 */
public enum StringDrawer {
  /** 左揃えで文字列を描画するドロワーです。 */
  LEFT {
    @Override
    public void draw(Graphics g, int x, int y, String... lines) {
      final FontMetrics fontMetrics = g.getFontMetrics();
      final int height = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

      y += fontMetrics.getMaxAscent();
      for (String line : lines) {
        g.drawString(line, x, y);
        y += height;
      }
    }
  },

  /** 中央揃えで文字列を描画するドロワーです。 */
  CENTER {
    @Override
    public void draw(Graphics g, int x, int y, String... lines) {
      final FontMetrics fontMetrics = g.getFontMetrics();
      final int height = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

      y += (fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2;
      y -= height * (lines.length - 1) / 2;
      for (String line : lines) {
        g.drawString(line, x - fontMetrics.stringWidth(line) / 2, y);
        y += height;
      }
    }
  },

  /** 右揃えで文字列を描画するドロワーです。 */
  RIGHT {
    @Override
    public void draw(Graphics g, int x, int y, String... lines) {
      final FontMetrics fontMetrics = g.getFontMetrics();
      final int height = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

      y += fontMetrics.getMaxAscent();
      for (String line : lines) {
        g.drawString(line, x - fontMetrics.stringWidth(line), y);
        y += height;
      }
    }
  },
  ;

  /**
   * このドロワーを使用して座標(x, y) に文字列(複数行) を描画します。
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @param x 文字列のx座標。
   * @param y 文字列のy座標。
   * @param lines 描画する文字列。
   */
  public abstract void draw(Graphics g, int x, int y, String... lines);
}
