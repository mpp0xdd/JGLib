package jglib.util.spec;

import java.awt.Rectangle;

public interface Rectangular extends Quadrangular {

  int width();

  int height();

  @Override
  default Rectangle asRectangle() {
    return new Rectangle(x(), y(), width(), height());
  }
}
