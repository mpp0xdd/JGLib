import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Gameのユーティリティ・メソッドのコレクションです。
 *
 * @author mpp
 */
public final class GameUtilities {

  private GameUtilities() {}

  /**
   * 指定されたミリ秒数の間，スリープ(一時的にゲームの実行を停止) させます。<br>
   * このメソッドは {@code TimeUnit.MILLISECONDS.sleep()} の単なるラッパーです。
   *
   * @param millis ミリ秒単位のスリープ時間の長さ
   * @see java.util.concurrent.TimeUnit#sleep(long)
   */
  public static void sleep(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException ie) {
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
    for (String line : lines) {
      g.drawString(line, x, y);
      y += height;
    }
  }

  /**
   * 座標(x, y) を右上隅として文字列(複数行) を描画します。
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @param x 文字列の右上隅のx座標。
   * @param y 文字列の右上隅のy座標。
   * @param lines 描画する文字列。
   */
  public static void drawStringFromTopRight(Graphics g, int x, int y, String... lines) {
    FontMetrics fontMetrics = g.getFontMetrics();
    final int height = fontMetrics.getMaxDescent() + fontMetrics.getMaxAscent();

    y += fontMetrics.getMaxAscent();
    for (String line : lines) {
      g.drawString(line, x - fontMetrics.stringWidth(line), y);
      y += height;
    }
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
    for (String line : lines) {
      g.drawString(line, x - fontMetrics.stringWidth(line) / 2, y);
      y += height;
    }
  }

  /**
   * 指定されたURLからopen済みのClipを取得します。
   *
   * @param url Clipを構築するURL
   * @return open済みのClip
   */
  public static Optional<Clip> loadClip(URL url) {
    Clip clip;
    try {
      clip = AudioSystem.getClip();
      try (AudioInputStream stream =
          AudioSystem.getAudioInputStream(clip.getFormat(), AudioSystem.getAudioInputStream(url))) {
        clip.open(stream);
      }
    } catch (Exception e) {
      (new IOException("Load failed: " + url, e)).printStackTrace();
      clip = null;
    }
    return Optional.ofNullable(clip);
  }

  /**
   * 指定されたFileからopen済みのClipを取得します。
   *
   * @param file Clipを構築するFile
   * @return open済みのClip
   */
  public static Optional<Clip> loadClip(File file) {
    Clip clip;
    try {
      clip = AudioSystem.getClip();
      try (AudioInputStream stream =
          AudioSystem.getAudioInputStream(
              clip.getFormat(), AudioSystem.getAudioInputStream(file))) {
        clip.open(stream);
      }
    } catch (Exception e) {
      (new IOException("Load failed: " + file, e)).printStackTrace();
      clip = null;
    }
    return Optional.ofNullable(clip);
  }

  /**
   * パス名pathnameで指定される実ファイルからopen済みのClipを取得します。
   *
   * @param pathname パス名文字列
   * @return open済みのClip
   */
  public static Optional<Clip> loadClip(String pathname) {
    return loadClip(new File(pathname));
  }

  /**
   * クリップのオーディオ・データの先頭から再生を開始します。
   *
   * @param clip 再生したいClip
   */
  public static void playClip(Clip clip) {
    clip.stop();
    clip.setFramePosition(0);
    clip.start();
  }

  /**
   * クリップのオーディオ・データの先頭からリピート再生を開始します。
   *
   * @param clip リピート再生したいClip
   */
  public static void repeatClip(Clip clip) {
    clip.stop();
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  /**
   * 指定されたURLからImageを取得します。
   *
   * @param url Imageを構築するURL
   * @return Image
   */
  public static Optional<BufferedImage> loadImage(URL url) {
    BufferedImage image;
    try {
      image = ImageIO.read(url);
    } catch (Exception e) {
      (new IOException("Load failed: " + url, e)).printStackTrace();
      image = null;
    }
    return Optional.ofNullable(image);
  }

  /**
   * 指定されたFileからImageを取得します。
   *
   * @param file Imageを構築するFile
   * @return Image
   */
  public static Optional<BufferedImage> loadImage(File file) {
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    } catch (Exception e) {
      (new IOException("Load failed: " + file, e)).printStackTrace();
      image = null;
    }
    return Optional.ofNullable(image);
  }

  /**
   * パス名pathnameで指定される実ファイルからImageを取得します。
   *
   * @param pathname パス名文字列
   * @return Image
   */
  public static Optional<BufferedImage> loadImage(String pathname) {
    return loadImage(new File(pathname));
  }

  /**
   * 指定されたURLからSpriteSheetを取得します。
   *
   * @param url スプライトシートを構築するURL
   * @param width 各グラフィックの横幅
   * @param height 各グラフィックの縦幅
   * @param rows スプライトシートの行数（並べられているグラフィックの数）
   * @param columns スプライトシートの列数（並べられているグラフィックの数）
   * @return SpriteSheet
   */
  public static Optional<SpriteSheet> loadSpriteSheet(
      URL url, int width, int height, int rows, int columns) {
    SpriteSheet spriteSheet;
    try {
      spriteSheet = new SpriteSheet(ImageIO.read(url), width, height, rows, columns);
    } catch (Exception e) {
      (new IOException("Load failed: " + url, e)).printStackTrace();
      spriteSheet = null;
    }
    return Optional.ofNullable(spriteSheet);
  }

  /**
   * 指定されたFileからSpriteSheetを取得します。
   *
   * @param file スプライトシートを構築するFile
   * @param width 各グラフィックの横幅
   * @param height 各グラフィックの縦幅
   * @param rows スプライトシートの行数（並べられているグラフィックの数）
   * @param columns スプライトシートの列数（並べられているグラフィックの数）
   * @return SpriteSheet
   */
  public static Optional<SpriteSheet> loadSpriteSheet(
      File file, int width, int height, int rows, int columns) {
    SpriteSheet spriteSheet;
    try {
      spriteSheet = new SpriteSheet(ImageIO.read(file), width, height, rows, columns);
    } catch (Exception e) {
      (new IOException("Load failed: " + file, e)).printStackTrace();
      spriteSheet = null;
    }
    return Optional.ofNullable(spriteSheet);
  }

  /**
   * パス名pathnameで指定される実ファイルからSpriteSheetを取得します。
   *
   * @param pathname パス名文字列
   * @param width 各グラフィックの横幅
   * @param height 各グラフィックの縦幅
   * @param rows スプライトシートの行数（並べられているグラフィックの数）
   * @param columns スプライトシートの列数（並べられているグラフィックの数）
   * @return SpriteSheet
   */
  public static Optional<SpriteSheet> loadSpriteSheet(
      String pathname, int width, int height, int rows, int columns) {
    return loadSpriteSheet(new File(pathname), width, height, rows, columns);
  }
}
