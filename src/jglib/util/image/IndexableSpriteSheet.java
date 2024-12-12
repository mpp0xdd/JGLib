package jglib.util.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Optional;
import jglib.util.image.IndexableSpriteSheet.Index;

public interface IndexableSpriteSheet<I extends Index> {

  public static <I extends Index> IndexableSpriteSheet<I> create(
      BufferedImage image, int width, int height, int rows, int columns, I index) {
    return new IndexableSpriteSheetImpl<I>(image, width, height, rows, columns, index);
  }

  interface Index {
    int index();
  }

  int width();

  int height();

  Optional<BufferedImage> getImage();

  Optional<BufferedImage> getImage(I index);

  void draw(Graphics g, int x, int y);

  I getIndex();

  void setIndex(I index);
}
