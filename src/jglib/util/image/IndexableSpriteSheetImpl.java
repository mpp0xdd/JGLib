package jglib.util.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Optional;
import jglib.util.image.IndexableSpriteSheet.Index;

class IndexableSpriteSheetImpl<I extends Index<I>> implements IndexableSpriteSheet<I> {

  private final SpriteSheet spriteSheet;
  private I index;

  public IndexableSpriteSheetImpl(
      BufferedImage image, int width, int height, int rows, int columns, I index) {
    this.spriteSheet = SpriteSheet.create(image, width, height, rows, columns);
    setIndex(index);
  }

  @Override
  public int width() {
    return spriteSheet.width();
  }

  @Override
  public int height() {
    return spriteSheet.height();
  }

  @Override
  public Optional<BufferedImage> getImage() {
    return spriteSheet.getImage();
  }

  @Override
  public Optional<BufferedImage> getImage(I index) {
    return spriteSheet.getImage(index.index());
  }

  @Override
  public void draw(Graphics g, int x, int y) {
    spriteSheet.draw(g, x, y);
  }

  @Override
  public I getIndex() {
    return index;
  }

  @Override
  public void setIndex(I index) {
    spriteSheet.setIndex(index.index());
    this.index = index;
  }

  @Override
  public void next() {
    setIndex(getIndex().next());
  }

  @Override
  public void previous() {
    setIndex(getIndex().previous());
  }
}
