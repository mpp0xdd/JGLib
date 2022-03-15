import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;


interface Movable {
  public abstract void move();
}

abstract class DrawableObject {
  protected int x;
  protected int y;

  public DrawableObject(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public abstract void draw(Graphics g);
}

abstract class FunctionalObject extends DrawableObject implements Movable {
  private final int ix;
  private final int iy;

  public IntFunction<Integer> func = x -> x;
  public IntPredicate domain = x -> 0 <= x && x < GameScreen.DEFAULT_PREFERRED_WIDTH;
  public IntPredicate range = y -> 0 <= y && y < GameScreen.DEFAULT_PREFERRED_HEIGHT;

  public FunctionalObject(int x, int y) {
    super(x, y);
    this.ix = x;
    this.iy = y;
  }

  @Override
  public void move() {
    y = func.apply(x++);

    if(!domain.test(x) || !range.test(y)) {
      x = ix;
      y = iy;
    }
  }

}

class FunctionalBall extends FunctionalObject {
  private Color color;
  private int r;
  private int d;

  public FunctionalBall(Color color, int r, int ix, int iy) {
    super(ix, iy);
    this.color = color;
    this.r = r;
    this.d = 2 * r;
  }

  @Override
  public void draw(Graphics g) {
    Color tmp = g.getColor();
    g.setColor(color);
    g.fillOval(x, y, d, d);
    g.setColor(tmp);
  }
}

class FunctionalString extends FunctionalObject {
  private String[] lines;
  private Font font;

  public FunctionalString(String str, Font font, int ix, int iy) {
    super(ix, iy);
    this.lines = str.split("\n");
    this.font = font;
  }

  @Override
  public void draw(Graphics g) {
//    GameUtilities.drawStringAfterCentering(g, font, x, y, lines);
    GameUtilities.drawString(g, font, x, y, lines);
  }
}

public class TestScreen extends GameScreen {
  private static int numOfCurrentInstances = 0;

  private final int id = ++numOfCurrentInstances;
  private FunctionalObject[] fObjs;

  public TestScreen(FunctionalObject[] fObjs) {
    this.fObjs = fObjs;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for(DrawableObject dObj : fObjs) {
      dObj.draw(g);
    }
  }

  @Override
  protected void runGameLoop() {
//    System.err.printf("id:%d: Game loop running..., Current Time: %d\n", id, System.currentTimeMillis());
    for(Movable mObj : fObjs) {
      mObj.move();
    }
    repaint();
    GameUtilities.sleep(10);
  }
}
