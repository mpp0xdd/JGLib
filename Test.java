import java.awt.Color;
import java.awt.Font;
import java.util.Optional;
import javax.sound.sampled.Clip;

public class Test {
  public static void main(String[] args) {
    TestWindow window = new TestWindow();
    window.setResizable(false);

    FunctionalObject[] fObjs = new FunctionalObject[4];
    fObjs[0] = new FunctionalBall(Color.red, 5, 0, 0);
    fObjs[1] = new FunctionalBall(Color.blue, 10, 0, 100);
    fObjs[1].func = x -> (int) (100 - 100 * Math.sin(x * 0.01));
    fObjs[2] = new FunctionalBall(Color.yellow, 10, 0, 100);
    fObjs[2].func = x -> (int) (100 - 100 * Math.cos(x * 0.01));
    Font font = new Font(Font.SERIF, Font.PLAIN, 20);
    fObjs[3] = new FunctionalString("Hello\nWorld", font, 0, 400);
    fObjs[3].func = x -> (int) (400 - 50 * Math.sin(x * 0.01));

    GameScreen screen = new TestScreen(fObjs);

    window.switchGameScreen(screen);
    window.pack();
    window.setVisible(true);

    Optional<Clip> clip = GameUtilities.loadClip(Test.class.getResource("bgm.mid"));
    // Optional<Clip> clip = GameUtilities.loadClip(new java.io.File("bgm.mid"));
    // Optional<Clip> clip = GameUtilities.loadClip("bgm.mid");
    clip.ifPresent(c -> c.start());

    screen.startGameLoop();
    GameUtilities.sleep(5000);
    screen.stopGameLoop();
    GameUtilities.sleep(1000);

    GameScreen screen2 = new EphemeralTestScreen();
    window.switchGameScreen(screen2);
    screen2.startGameLoop();

    Optional<Clip> clip2 = GameUtilities.loadClip("effect.wav");
    clip2.ifPresent(c -> c.start());

    screen2.joinGameLoop();
    System.err.println("テスト完了");
  }
}
