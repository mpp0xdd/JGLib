package jglib.image;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class SpriteSheetImpl implements SpriteSheet {

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
   * {@link draw(Graphics)} で描画されるスプライトシートのグラフィック番号（添え字）を表します。
   *
   * @see #getIndex()
   */
  private int index;

  public SpriteSheetImpl(BufferedImage image, int width, int height, int rows, int columns) {
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
    this.afterLast();
  }

  @Override
  public int x() {
    return this.point.x;
  }

  @Override
  public void setX(int x) {
    this.point.x = x;
  }

  @Override
  public int y() {
    return this.point.y;
  }

  @Override
  public void setY(int y) {
    this.point.y = y;
  }

  @Override
  public Point getLocation() {
    return this.point.getLocation();
  }

  @Override
  public void setLocation(Point p) {
    this.point.setLocation(p);
  }

  @Override
  public void setLocation(int x, int y) {
    this.point.setLocation(x, y);
  }

  @Override
  public int width() {
    return width;
  }

  @Override
  public int height() {
    return height;
  }

  @Override
  public Optional<BufferedImage> getImage() {
    if (isBeforeFirst() || isAfterLast()) return Optional.empty();
    return Optional.of(
        image.getSubimage(
            (getIndex() % columns) * width, (getIndex() / columns) * height, width, height));
  }

  @Override
  public Optional<BufferedImage> getImage(int index) {
    final int currentIndex = getIndex(); // Back up the current index
    try {
      setIndex(index);
      return getImage();
    } finally {
      setIndex(currentIndex); // Restore the index
    }
  }

  @Override
  public void draw(Graphics g) {
    getImage().ifPresent(image -> g.drawImage(image, point.x, point.y, null));
  }

  @Override
  public int getIndex() {
    return this.index;
  }

  @Override
  public void setIndex(int index) {
    if (index < -1 || index > this.rows * this.columns) {
      throw (new IndexOutOfBoundsException(
          String.format("%d (index must be between -1 and %d)", index, rows * columns)));
    }
    this.index = index;
  }

  @Override
  public void first() {
    setIndex(0);
  }

  @Override
  public boolean isFirst() {
    return getIndex() == 0;
  }

  @Override
  public void beforeFirst() {
    first();
    previous();
  }

  @Override
  public boolean isBeforeFirst() {
    return getIndex() == -1;
  }

  @Override
  public void last() {
    setIndex(this.rows * this.columns - 1);
  }

  @Override
  public boolean isLast() {
    return getIndex() == this.rows * this.columns - 1;
  }

  @Override
  public void afterLast() {
    last();
    next();
  }

  @Override
  public boolean isAfterLast() {
    return getIndex() == this.rows * this.columns;
  }

  @Override
  public void previous() {
    if (!isBeforeFirst()) setIndex(getIndex() - 1);
  }

  @Override
  public void next() {
    if (!isAfterLast()) setIndex(getIndex() + 1);
  }
}
