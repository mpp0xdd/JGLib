package jglib.image;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Optional;
import jglib.util.spec.Drawable;
import jglib.util.spec.Rectangular;

/**
 * スプライトシート(*) です。<br>
 * (*) 複数のグラフィックをタイル状（グリッド状）に並べたイメージファイル
 *
 * @author mpp
 */
public interface SpriteSheet extends Drawable, Rectangular {

  /**
   * 指定された {@link BufferedImage} で新しい SpriteSheet を構築します。<br>
   * SpriteSheet の座標の初期値は座標空間の原点 (0, 0) に，グラフィック番号（添え字）の初期値は最大値にそれぞれなることに注意してください。<br>
   * SpriteSheet の構築に失敗した場合（imageにnullが渡された場合など）は， {@link RuntimeException} をスローします。
   *
   * @param image スプライトシート
   * @param width 各グラフィックの横幅
   * @param height 各グラフィックの縦幅
   * @param rows スプライトシートの行数（並べられているグラフィックの数）
   * @param columns スプライトシートの列数（並べられているグラフィックの数）
   * @throws RuntimeException SpriteSheetの構築に失敗した場合
   * @see getIndex()
   */
  public static SpriteSheet create(
      BufferedImage image, int width, int height, int rows, int columns) {
    return new SpriteSheetImpl(image, width, height, rows, columns);
  }

  /**
   * この SpriteSheet の X 座標を返します。
   *
   * @return このスプライトシートの X 座標
   */
  @Override
  int x();

  /**
   * この SpriteSheet の X 座標を設定します。
   *
   * @param x 設定される X 座標
   */
  void setX(int x);

  /**
   * この SpriteSheet の Y 座標を返します。
   *
   * @return このスプライトシートの Y 座標
   */
  @Override
  int y();

  /**
   * この SpriteSheet の Y 座標を設定します。
   *
   * @param y 設定される Y 座標
   */
  void setY(int y);

  /**
   * この SpriteSheet の位置を返します。
   *
   * @return 同じ位置の，点のコピー
   */
  Point getLocation();

  /**
   * この SpriteSheet の位置を，指定された位置に設定します。
   *
   * @param p この SpriteSheet の新しい位置になる点
   */
  void setLocation(Point p);

  /**
   * この SpriteSheet の位置を指定された位置に変更します。
   *
   * @param x 新しい位置の X 座標
   * @param y 新しい位置の Y 座標
   */
  void setLocation(int x, int y);

  /** この SpriteSheet の横幅を返します。 */
  @Override
  int width();

  /** この SpriteSheet の縦幅を返します。 */
  @Override
  int height();

  /**
   * この SpriteSheet の {@link getIndex()} が返すグラフィック番号（現在のグラフィック番号）が指すグラフィックを返します。<br>
   * （現在のグラフィック番号が指すグラフィックが存在しない場合(*)は 空のOptionalインスタンスが返されることに注意してください）<br>
   * (*) {@link isBeforeFirst()} または {@link isAfterLast()} が true を返す場合
   *
   * @return グラフィック番号が指すグラフィック
   * @see getIndex()
   * @see setIndex(int)
   */
  Optional<BufferedImage> getImage();

  /**
   * 指定されたグラフィック番号が指すグラフィックを返します（この SpriteSheet の現在のグラフィック番号の値は変更されません）。<br>
   * ただし，指定されたグラフィック番号が指すグラフィックが存在しない場合(*) は 空のOptionalインスタンスを返します。<br>
   * また無効なグラフィック番号（グラフィック番号の有効範囲については {@link getIndex()} を参照してください）が指定された場合は {@link
   * IndexOutOfBoundsException} をスローします。<br>
   * (*) {@link isBeforeFirst()} または {@link isAfterLast()} が true を返すグラフィック番号の場合
   *
   * @param index グラフィック番号
   * @return グラフィック番号が指すグラフィック
   * @see getIndex()
   * @see setIndex(int)
   * @throws IndexOutOfBoundsException グラフィック番号に無効な値を指定された場合
   */
  Optional<BufferedImage> getImage(int index);

  /**
   * この SpriteSheet の描画を行います。<br>
   * 描画は，この SpriteSheet の {@link getIndex()} が返すグラフィック番号が指すグラフィックが存在する場合のみ行われることに注意してください。<br>
   * （グラフィック番号が指すグラフィックが存在しない場合(*) にこのメソッドが呼び出された時は，このメソッドは何も行いません）<br>
   * (*) {@link isBeforeFirst()} または {@link isAfterLast()} が true を返す場合
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @see getIndex()
   * @see setIndex(int)
   */
  @Override
  void draw(Graphics g);

  /**
   * {@link draw(Graphics)} で描画されるスプライトシートのグラフィック番号（添え字）を返します。<br>
   * グラフィック番号とは，並んでいるグラフィックの一番左上から右下にかけて振られた番号（添え字）のことです。<br>
   * （ {@link draw(Graphics)} は {@code getIndex()} が返すグラフィック番号が表すグラフィックの描画を行います）<br>
   * グラフィック番号の最小値（最大値）が実際のグラフィック番号より一つ小さい（大きい）値になっていることに注意してください。<br>
   * これはユーザの利便のためです。
   *
   * <table border="1" style="text-align:center">
   *   <caption>2行3列のスプライトシートの場合</caption>
   *   <tr style="background-color:#dee3e9">
   *     <th>行番号</th>
   *     <th>列番号</th>
   *     <th>グラフィック番号</th>
   *   </tr>
   *   <tr>
   *     <td>-</td>
   *     <td>-</td>
   *     <td>-1</td>
   *   </tr>
   *   <tr>
   *     <td>0</td>
   *     <td>0</td>
   *     <td>0</td>
   *   </tr>
   *   <tr>
   *     <td>0</td>
   *     <td>1</td>
   *     <td>1</td>
   *   </tr>
   *   <tr>
   *     <td>0</td>
   *     <td>2</td>
   *     <td>2</td>
   *   </tr>
   *   <tr>
   *     <td>1</td>
   *     <td>0</td>
   *     <td>3</td>
   *   </tr>
   *   <tr>
   *     <td>1</td>
   *     <td>1</td>
   *     <td>4</td>
   *   </tr>
   *   <tr>
   *     <td>1</td>
   *     <td>2</td>
   *     <td>5</td>
   *   </tr>
   *   <tr>
   *     <td>-</td>
   *     <td>-</td>
   *     <td>6</td>
   *   </tr>
   * </table>
   *
   * @return グラフィック番号
   */
  int getIndex();

  /**
   * {@link draw(Graphics)} で描画されるスプライトシートのグラフィック番号を設定します。<br>
   * 無効なグラフィック番号（グラフィック番号の有効範囲については {@link getIndex()} を参照してください）が設定されようとした場合は {@link
   * IndexOutOfBoundsException} をスローします。
   *
   * @param index 設定される グラフィック番号
   * @throws IndexOutOfBoundsException グラフィック番号に無効な値を設定しようとした場合
   * @see getIndex()
   */
  void setIndex(int index);

  /** この SpriteSheet のグラフィック番号をスプライトシートの先頭に移動させます。 */
  void first();

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの先頭にあるかどうかを取得します。
   *
   * @return グラフィック番号が先頭にある場合はtrue、そうでない場合はfalse
   */
  boolean isFirst();

  /** この SpriteSheet のグラフィック番号をスプライトシートの先端，つまり先頭の直前（1つ前）に移動します。 */
  void beforeFirst();

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの先端（先頭の直前（1つ前））にあるかどうかを取得します。
   *
   * @return グラフィック番号が先端にある場合はtrue、そうでない場合はfalse
   */
  boolean isBeforeFirst();

  /** この SpriteSheet のグラフィック番号をスプライトシートの最後に移動させます。 */
  void last();

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの最後にあるかどうかを取得します。
   *
   * @return グラフィック番号が最後にある場合はtrue、そうでない場合はfalse
   */
  boolean isLast();

  /** この SpriteSheet のグラフィック番号をスプライトシートの終端，つまり最後の直後（1つ後）に移動します。 */
  void afterLast();

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの終端（最後の直後（1つ後））にあるかどうかを取得します。
   *
   * @return グラフィック番号が終端にある場合はtrue、そうでない場合はfalse
   */
  boolean isAfterLast();

  /**
   * この SpriteSheet のグラフィック番号を一つ前の番号に移動します。<br>
   * ただし，{@link isBeforeFirst()} が trueを返す場合（現在のグラフィック番号が最小値の場合）は移動を行いません。
   */
  void previous();

  /**
   * この SpriteSheet のグラフィック番号を現在の位置から順方向に1つ移動します。<br>
   * ただし，{@link isAfterLast()} が trueを返す場合（現在のグラフィック番号が最大値の場合）は移動を行いません。
   */
  void next();
}
