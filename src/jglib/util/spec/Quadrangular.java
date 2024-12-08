package jglib.util.spec;

import java.awt.Rectangle;

public interface Quadrangular {

  int x();

  int y();

  Rectangle asRectangle();

  default boolean contains(Quadrangular other) {
    return this.asRectangle().contains(other.asRectangle());
  }

  default boolean intersects(Quadrangular other) {
    return this.asRectangle().intersects(other.asRectangle());
  }
}
