package jglib.util.spec;

import java.awt.Rectangle;

public interface Square extends Quadrangular {

  int length();

  @Override
  default Rectangle asRectangle() {
    return new Rectangle(x(), y(), length(), length());
  }
}
