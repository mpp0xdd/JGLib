package jglib.component;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Objects;
import java.util.function.Consumer;

class OffScreen {

  public static OffScreen create(Image offScreen) {
    return new OffScreen(offScreen);
  }

  private final Image offScreen;
  private final Graphics graphics;

  private OffScreen(Image offScreen) {
    this.offScreen = Objects.requireNonNull(offScreen, "offScreen");
    this.graphics = Objects.requireNonNull(offScreen.getGraphics(), "graphics");
  }

  public int width() {
    return offScreen.getWidth(null);
  }

  public int height() {
    return offScreen.getHeight(null);
  }

  public void clear() {
    graphics.clearRect(0, 0, width(), height());
  }

  public void render(Consumer<? super Graphics> renderer) {
    renderer.accept(graphics);
  }

  public void draw(Graphics g) {
    g.drawImage(offScreen, 0, 0, null);
  }
}
