import java.awt.Color;
import java.awt.Font;


public class Test {
  public static void main(String[] args) {
    TestWindow window = new TestWindow();
    window.setResizable(false);

    FunctionalObject[] fObjs = new FunctionalObject[4];
    fObjs[0] = new FunctionalBall(Color.red, 5, 0, 0);
    fObjs[1] = new FunctionalBall(Color.blue, 10, 0, 100);
    fObjs[1].func = x -> (int)(100 - 100 * Math.sin(x*0.01));
    fObjs[2] = new FunctionalBall(Color.yellow, 10, 0, 100);
    fObjs[2].func = x -> (int)(100 - 100 * Math.cos(x*0.01));
    Font font = new Font(Font.SERIF, Font.PLAIN, 20);
    fObjs[3] = new FunctionalString("Hello\nWorld", font, 0, 400);
    fObjs[3].func = x -> (int)(400 - 50 * Math.sin(x*0.01));

    FunctionalObject[] fObjs2 = new FunctionalObject[1];
    fObjs2[0] = new FunctionalBall(Color.green, 10, 0, 400);
    fObjs2[0].func = x -> 400 - x;

    TestScreen screen = new TestScreen(fObjs);
    TestScreen screen2 = new TestScreen(fObjs2);

    window.switchGameScreen(screen);
    window.pack();
    window.setVisible(true);

    while(true) {
      window.switchGameScreen(screen);
      screen.startGameLoop();
      GameUtilities.sleep(10000);
      screen.stopGameLoop();

      window.switchGameScreen(screen2);
      screen2.startGameLoop();
      GameUtilities.sleep(10000);
      screen2.stopGameLoop();
    }
  }
}
