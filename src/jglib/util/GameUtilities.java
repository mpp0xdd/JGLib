package jglib.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import jglib.image.SpriteSheet;

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
      GameLogger.getLogger().warning(new LoadException(url, e));
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
      GameLogger.getLogger().warning(new LoadException(file, e));
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
   * Clipの音量を設定します。
   *
   * @param clip 音量を設定したいClip
   * @param percentage 0f～1f
   */
  public static void setVolume(Clip clip, float percentage) {
    FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    float range = control.getMaximum() - control.getMinimum();
    float volume = control.getMinimum() + range * percentage;
    control.setValue(volume);
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
      GameLogger.getLogger().warning(new LoadException(url, e));
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
      GameLogger.getLogger().warning(new LoadException(file, e));
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
      spriteSheet = SpriteSheet.create(ImageIO.read(url), width, height, rows, columns);
    } catch (Exception e) {
      GameLogger.getLogger().warning(new LoadException(url, e));
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
      spriteSheet = SpriteSheet.create(ImageIO.read(file), width, height, rows, columns);
    } catch (Exception e) {
      GameLogger.getLogger().warning(new LoadException(file, e));
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
