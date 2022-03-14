import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.concurrent.TimeUnit;


/**
 * Gameのユーティリティ・メソッドのコレクションです。
 *
 * @author mpp0xdd
 */
public final class GameUtilities {

  private GameUtilities() {}

  /**
   * 指定されたミリ秒数の間，スリープ(一時的にゲームの実行を停止) させます。<br>
   * このメソッドは {@code TimeUnit.MILLISECONDS.sleep()} の単なるラッパーです。
   * @param millis ミリ秒単位のスリープ時間の長さ
   * @see java.util.concurrent.TimeUnit#sleep(long)
   */
  public static void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    }
    catch(InterruptedException ie) {
      ie.printStackTrace();
    }
  }

  /**
   * 座標(x, y) を左上隅として文字列(複数行) を描画します。
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @param x 文字列の左上隅のx座標。
   * @param y 文字列の左上隅のy座標。
   * @param lines 描画する文字列。
   */
  public static void drawString(Graphics g, int x, int y, String... lines) {
    FontMetrics fontMetrics = g.getFontMetrics();
    final int height = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

    y += fontMetrics.getMaxAscent();
    for(String line : lines) {
      g.drawString(line, x, y);
      y += height;
    }
  }

  /**
   * 座標(x, y) を左上隅として文字列(複数行) を描画します。
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @param font 文字列のフォント。
   * @param x 文字列の左上隅のx座標。
   * @param y 文字列の左上隅のy座標。
   * @param lines 描画する文字列。
   */
  public static void drawString(Graphics g, Font font, int x, int y, String... lines) {
    Font tmp = g.getFont();
    g.setFont(font);
    drawString(g, x, y, lines);
    g.setFont(tmp);
  }

  /**
   * 座標(x, y) を中心として文字列(複数行) を描画します。
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @param x 文字列の左上隅のx座標。
   * @param y 文字列の左上隅のy座標。
   * @param lines 描画する文字列。
   */
  public static void drawStringAfterCentering(Graphics g, int x, int y, String... lines) {
    FontMetrics fontMetrics = g.getFontMetrics();
    final int height = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

    y += (fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2;
    for(String line : lines) {
      g.drawString(line, x - fontMetrics.stringWidth(line) / 2, y);
      y += height;
    }
  }

  /**
   * 座標(x, y) を中心として文字列(複数行) を描画します。
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @param font 文字列のフォント。
   * @param x 文字列の左上隅のx座標。
   * @param y 文字列の左上隅のy座標。
   * @param lines 描画する文字列。
   */
  public static void drawStringAfterCentering(Graphics g, Font font, int x, int y, String... lines) {
    Font tmp = g.getFont();
    g.setFont(font);
    drawStringAfterCentering(g, x, y, lines);
    g.setFont(tmp);
  }
}
