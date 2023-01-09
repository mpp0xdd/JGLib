import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;

public class EphemeralTestScreen extends GameScreen {
  private static final long LIFESPAN = 10000;

  private long birthTime = System.currentTimeMillis();

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    final int width = getWidth();
    final int height = getHeight();
    final long currentTime = System.currentTimeMillis();
    final Date currentDate = new Date(currentTime);
    g.setFont(new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, 30));
    GameUtilities.drawStringAfterCentering(g, width / 2, height / 2, currentDate.toString());
  }

  @Override
  protected void runGameLoop() {
    if (System.currentTimeMillis() - birthTime > LIFESPAN) {
      stopGameLoop();
    }

    repaint();
  }
}
