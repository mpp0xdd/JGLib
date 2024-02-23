package jglib.component;

import java.awt.Graphics;

public interface SubGameScreen {

  int x();

  int y();

  int width();

  int height();

  void draw(Graphics g);
}
