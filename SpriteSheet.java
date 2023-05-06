import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * イメージ（ {@link BufferedImage} ）をラップし，スプライトシート(*) として扱えるようにするクラスです。<br>
 * (*) 複数のグラフィックをタイル状（グリッド状）に並べたイメージファイル
 *
 * @author mpp
 */
public class SpriteSheet {

  /** スプライトシートそのものを表します。 */
  private final BufferedImage image;

  /** 各グラフィックの横幅を表します。 */
  private final int width;

  /** 各グラフィックの縦幅を表します。 */
  private final int height;

  /** スプライトシートの行数（並べられているグラフィックの数）を表します。 */
  private final int rows;

  /** スプライトシートの列数（並べられているグラフィックの数）を表します。 */
  private final int columns;

  /** この SpriteSheet の座標を表します。初期値は座標空間の原点 (0, 0) です。 */
  private final Point point;

  /**
   * {@link draw(Graphics)} で描画されるスプライトシートのグラフィック番号（添え字）を表します。<br>
   *
   * @see getIndex()
   */
  private int index;

  /**
   * 指定された {@link BufferedImage} で新しい SpriteSheet を構築します。<br>
   * SpriteSheet の座標の初期値は座標空間の原点 (0, 0) となることに注意してください。<br>
   * SpriteSheet の構築に失敗した場合（imageにnullが渡された場合など）は， {@link RuntimeException} をスローします。
   *
   * @param image スプライトシート
   * @param width 各グラフィックの横幅
   * @param height 各グラフィックの縦幅
   * @param rows スプライトシートの行数（並べられているグラフィックの数）
   * @param columns スプライトシートの列数（並べられているグラフィックの数）
   * @throws RuntimeException SpriteSheetの構築に失敗した場合
   */
  public SpriteSheet(BufferedImage image, int width, int height, int rows, int columns) {
    this.image = Objects.requireNonNull(image);
    this.width = width;
    this.height = height;
    this.rows = rows;
    this.columns = columns;
    this.point = new Point();

    // Validate each field except image
    final List<RuntimeException> validationResults = new ArrayList<>();
    if (this.width <= 0) {
      validationResults.add(
          new IllegalArgumentException(
              String.format("%s (width value must be a positive number)", this.width)));
    }
    if (this.height <= 0) {
      validationResults.add(
          new IllegalArgumentException(
              String.format("%s (height value must be a positive number)", this.height)));
    }
    if (this.rows <= 0) {
      validationResults.add(
          new IllegalArgumentException(
              String.format("%s (rows value must be a positive number)", this.rows)));
    }
    if (this.columns <= 0) {
      validationResults.add(
          new IllegalArgumentException(
              String.format("%s (columns value must be a positive number)", this.columns)));
    }
    if (this.image.getWidth() * this.image.getHeight()
        != (this.width * this.height) * (this.rows * this.columns)) {
      validationResults.add(
          new IllegalArgumentException(
              String.format(
                  "The size of the image (%d*%d) and the total size of the grid (%d*%d*%d*%d) must"
                      + " be equal",
                  this.image.getWidth(),
                  this.image.getHeight(),
                  this.width,
                  this.height,
                  this.rows,
                  this.columns)));
    }
    Optional<RuntimeException> exception =
        validationResults.stream()
            .reduce(
                (e1, e2) -> {
                  e1.addSuppressed(e2);
                  return e1;
                });
    if (exception.isPresent()) {
      throw exception.get();
    }
    this.beforeFirst();
  }

  /**
   * この SpriteSheet の X 座標を返します。
   *
   * @return このスプライトシートの X 座標
   */
  public int getX() {
    return this.point.x;
  }

  /**
   * この SpriteSheet の X 座標を設定します。
   *
   * @param x 設定される X 座標
   */
  public void setX(int x) {
    this.point.x = x;
  }

  /**
   * この SpriteSheet の Y 座標を返します。
   *
   * @return このスプライトシートの Y 座標
   */
  public int getY() {
    return this.point.y;
  }

  /**
   * この SpriteSheet の Y 座標を設定します。
   *
   * @param y 設定される Y 座標
   */
  public void setY(int y) {
    this.point.y = y;
  }

  /**
   * この SpriteSheet の位置を返します。
   *
   * @return 同じ位置の，点のコピー
   */
  public Point getLocation() {
    return this.point.getLocation();
  }

  /**
   * この SpriteSheet の位置を，指定された位置に設定します。
   *
   * @param p この SpriteSheet の新しい位置になる点
   */
  public void setLocation(Point p) {
    this.point.setLocation(p);
  }

  /**
   * この SpriteSheet の位置を指定された位置に変更します。
   *
   * @param x 新しい位置の X 座標
   * @param y 新しい位置の Y 座標
   */
  public void setLocation(int x, int y) {
    this.point.setLocation(x, y);
  }

  /**
   * この SpriteSheet の描画を行います。<br>
   * 描画は，この SpriteSheet の {@link getIndex()} が返すグラフィック番号が有効な場合(*) にのみ行われることに注意してください。<br>
   * （グラフィック番号が無効の場合にこのメソッドが呼び出された時は，何も行わずに直ちに return します）<br>
   * (*) {@link isBeforeFirst()} または {@link isAfterLast()} が true を返す場合
   *
   * @param g 文字列の描画に使用するグラフィックスコンテキスト。
   * @see getIndex()
   * @see setIndex(int)
   */
  public void draw(Graphics g) {
    if (isBeforeFirst() || isAfterLast()) return;
    BufferedImage grid =
        image.getSubimage((index % columns) * width, (index / columns) * height, width, height);
    g.drawImage(grid, point.x, point.y, null);
  }

  /**
   * {@link draw(Graphics)} で描画されるスプライトシートのグラフィック番号（添え字）を返します。<br>
   * グラフィック番号とは，並んでいるグラフィックの一番左上から右下にかけて振られた番号（添え字）のことです。<br>
   * （ {@link draw(Graphics)} は {@code getIndex()} が返すグラフィック番号が表すグラフィックの描画を行います）<br>
   * グラフィック番号の最小値（最大値）が有効なグラフィック番号より一つ小さい（大きい）値になっていることに注意してください。<br>
   * これはユーザの利便のためです。
   *
   * <table border="1">
   *   <caption>2行3列のスプライトシートの場合</caption>
   *   <tr align="center">
   *     <th bgcolor="#dee3e9">行番号</th>
   *     <th bgcolor="#dee3e9">列番号</th>
   *     <th bgcolor="#dee3e9">グラフィック番号</th>
   *   </tr>
   *   <tr align="center">
   *     <td>-</td>
   *     <td>-</td>
   *     <td>-1</td>
   *   </tr>
   *   <tr align="center">
   *     <td>0</td>
   *     <td>0</td>
   *     <td>0</td>
   *   </tr>
   *   <tr align="center">
   *     <td>0</td>
   *     <td>1</td>
   *     <td>1</td>
   *   </tr>
   *   <tr align="center">
   *     <td>0</td>
   *     <td>2</td>
   *     <td>2</td>
   *   </tr>
   *   <tr align="center">
   *     <td>1</td>
   *     <td>0</td>
   *     <td>3</td>
   *   </tr>
   *   <tr align="center">
   *     <td>1</td>
   *     <td>1</td>
   *     <td>4</td>
   *   </tr>
   *   <tr align="center">
   *     <td>1</td>
   *     <td>2</td>
   *     <td>5</td>
   *   </tr>
   *   <tr align="center">
   *     <td>-</td>
   *     <td>-</td>
   *     <td>6</td>
   *   </tr>
   * </table>
   *
   * @return グラフィック番号
   */
  public int getIndex() {
    return this.index;
  }

  /**
   * {@link draw(Graphics)} で描画されるスプライトシートのグラフィック番号を設定します。<br>
   * 無効なグラフィック番号（グラフィック番号の有効範囲については {@link getIndex()} を参照してください）が設定されようとした場合は {@link
   * IllegalArgumentException} をスローします。
   *
   * @param index 設定される グラフィック番号
   * @throws IllegalArgumentException グラフィック番号に無効な値を設定しようとした場合
   * @see getIndex()
   */
  public void setIndex(int index) {
    if (index < -1 || index > this.rows * this.columns) {
      throw (new IllegalArgumentException(
          String.format("%d (index must be between -1 and %d)", index, rows * columns)));
    }
    this.index = index;
  }

  /** この SpriteSheet のグラフィック番号をスプライトシートの先頭に移動させます。 */
  public void first() {
    setIndex(0);
  }

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの先頭にあるかどうかを取得します。
   *
   * @return グラフィック番号が先頭にある場合はtrue、そうでない場合はfalse
   */
  public boolean isFirst() {
    return getIndex() == 0;
  }

  /** この SpriteSheet のグラフィック番号をスプライトシートの先端，つまり先頭の直前（1つ前）に移動します。 */
  public void beforeFirst() {
    first();
    setIndex(getIndex() - 1);
  }

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの先端（先頭の直前（1つ前））にあるかどうかを取得します。
   *
   * @return グラフィック番号が先端にある場合はtrue、そうでない場合はfalse
   */
  public boolean isBeforeFirst() {
    return getIndex() == -1;
  }

  /** この SpriteSheet のグラフィック番号をスプライトシートの最後に移動させます。 */
  public void last() {
    setIndex(this.rows * this.columns - 1);
  }

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの最後にあるかどうかを取得します。
   *
   * @return グラフィック番号が最後にある場合はtrue、そうでない場合はfalse
   */
  public boolean isLast() {
    return getIndex() == this.rows * this.columns - 1;
  }

  /** この SpriteSheet のグラフィック番号をスプライトシートの終端，つまり最後の直後（1つ後）に移動します。 */
  public void afterLast() {
    last();
    setIndex(getIndex() + 1);
  }

  /**
   * この SpriteSheet のグラフィック番号がスプライトシートの終端（最後の直後（1つ後））にあるかどうかを取得します。
   *
   * @return グラフィック番号が終端にある場合はtrue、そうでない場合はfalse
   */
  public boolean isAfterLast() {
    return getIndex() == this.rows * this.columns;
  }

  /**
   * この SpriteSheet のグラフィック番号を一つ前の番号に移動します。<br>
   * ただし，{@link isBeforeFirst()} が trueを返す場合（現在のグラフィック番号が最小値の場合）は移動を行いません。
   */
  public void previous() {
    if (!isBeforeFirst()) setIndex(getIndex() - 1);
  }

  /**
   * この SpriteSheet のグラフィック番号を現在の位置から順方向に1つ移動します。<br>
   * ただし，{@link isAfterLast()} が trueを返す場合（現在のグラフィック番号が最大値の場合）は移動を行いません。
   */
  public void next() {
    if (!isAfterLast()) setIndex(getIndex() + 1);
  }
}
