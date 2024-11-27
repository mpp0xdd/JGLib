package jglib.test;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import jglib.component.GameScreen;
import jglib.component.GameWindow;
import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;
import jglib.util.StringDrawer;

@TestClass
class StringDrawerTest {

  class TestScreen extends GameScreen {

    public TestScreen() {}

    public TestScreen(int width, int height) {
      super(width, height);
    }

    @Override
    protected void runGameLoop() {}

    @Override
    protected void paintGameComponent(Graphics g) {
      drawGrid(g, 80);

      g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 43));

      String text =
          """
              Hello
              Java
              World
              """;
      StringDrawer.LEFT.draw(g, 0, 0, text);
      StringDrawer.CENTER.draw(g, getWidth() / 2, getHeight() / 2, text);
      StringDrawer.RIGHT.draw(g, getWidth(), 0, text);
    }

    private void drawGrid(Graphics g, int size) {
      final Graphics2D g2 = (Graphics2D) g;
      final Composite backup = g2.getComposite();
      try {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));

        for (int x = 0; x < getWidth(); x += size) {
          g2.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += size) {
          g2.drawLine(0, y, getWidth(), y);
        }

      } finally {
        g2.setComposite(backup);
      }
    }
  }

  @TestMethod
  void test() {
    GameWindow window = new GameWindow("StringDrawerTest");
    TestScreen screen = new TestScreen();

    window.switchGameScreen(screen);
    window.setVisible(true);
  }
}
