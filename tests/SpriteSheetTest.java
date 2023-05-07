import static java.util.Objects.*;

import java.awt.Point;
import java.awt.image.BufferedImage;

@TestClass
public class SpriteSheetTest {

  // Image for test
  private final int width = 192;
  private final int height = 96;
  private final int imageType = BufferedImage.TYPE_INT_RGB;
  private final BufferedImage image = new BufferedImage(width, height, imageType);

  @TestMethod
  void testConstructor() {
    Test.assertThrows(
        null, "new SpriteSheet(image, 32, 32, 3, 6)", () -> new SpriteSheet(image, 32, 32, 3, 6));
    Test.assertThrows(
        null, "new SpriteSheet(image, 32, 32, 6, 3)", () -> new SpriteSheet(image, 32, 32, 6, 3));

    Test.assertThrows(
        NullPointerException.class,
        "new SpriteSheet(null, 32, 32, 6, 3)",
        () -> new SpriteSheet(null, 32, 32, 6, 3));

    Exception exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 0, 32, 6, 3)",
                () -> new SpriteSheet(image, 0, 32, 6, 3))
            .get();
    assert exception.getMessage().equals("0 (width value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (0*32*6*3) must be"
                + " equal");
    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, -1, 32, 6, 3)",
                () -> new SpriteSheet(image, -1, 32, 6, 3))
            .get();
    assert exception.getMessage().equals("-1 (width value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (-1*32*6*3) must be"
                + " equal");

    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 32, 0, 6, 3)",
                () -> new SpriteSheet(image, 32, 0, 6, 3))
            .get();
    assert exception.getMessage().equals("0 (height value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*0*6*3) must be"
                + " equal");
    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 32, -1, 6, 3)",
                () -> new SpriteSheet(image, 32, -1, 6, 3))
            .get();
    assert exception.getMessage().equals("-1 (height value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*-1*6*3) must be"
                + " equal");

    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 32, 32, 0, 3)",
                () -> new SpriteSheet(image, 32, 32, 0, 3))
            .get();
    assert exception.getMessage().equals("0 (rows value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*0*3) must be"
                + " equal");
    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 32, 32, -1, 3)",
                () -> new SpriteSheet(image, 32, 32, -1, 3))
            .get();
    assert exception.getMessage().equals("-1 (rows value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*-1*3) must be"
                + " equal");

    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 32, 32, 6, 0)",
                () -> new SpriteSheet(image, 32, 32, 6, 0))
            .get();
    assert exception.getMessage().equals("0 (columns value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*6*0) must be"
                + " equal");
    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, 32, 32, 6, -1)",
                () -> new SpriteSheet(image, 32, 32, 6, -1))
            .get();
    assert exception.getMessage().equals("-1 (columns value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*6*-1) must be"
                + " equal");

    exception =
        Test.assertThrows(
                IllegalArgumentException.class,
                "new SpriteSheet(image, -1, -2, -3, -4)",
                () -> new SpriteSheet(image, -1, -2, -3, -4))
            .get();
    assert exception.getMessage().equals("-1 (width value must be a positive number)");
    assert exception.getSuppressed().length == 4;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals("-2 (height value must be a positive number)");
    assert exception
        .getSuppressed()[1]
        .getMessage()
        .equals("-3 (rows value must be a positive number)");
    assert exception
        .getSuppressed()[2]
        .getMessage()
        .equals("-4 (columns value must be a positive number)");
    assert exception
        .getSuppressed()[3]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (-1*-2*-3*-4) must be"
                + " equal");
  }

  @TestMethod
  void testAccessor() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert target.getLocation().equals(new Point());
    assert target.getX() == 0;
    assert target.getY() == 0;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.setX(42);
    assert target.getLocation().equals(new Point(42, 0));
    assert target.getX() == 42;
    assert target.getY() == 0;

    target.setY(24);
    assert target.getLocation().equals(new Point(42, 24));
    assert target.getX() == 42;
    assert target.getY() == 24;

    target.setLocation(1, 2);
    assert target.getLocation().equals(new Point(1, 2));
    assert target.getX() == 1;
    assert target.getY() == 2;

    Point newPoint = new Point(100, 200);
    target.setLocation(newPoint);
    assert target.getLocation().equals(newPoint);
    assert target.getLocation() != newPoint;
    assert target.getX() == 100;
    assert target.getY() == 200;

    Test.assertThrows(
        NullPointerException.class, "target.setLocation(null)", () -> target.setLocation(null));

    target.setIndex(-1);
    assert target.getIndex() == -1;
    assert !target.isFirst();
    assert target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.setIndex(0);
    assert target.getIndex() == 0;
    assert target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.setIndex(1);
    assert target.getIndex() == 1;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.setIndex(17);
    assert target.getIndex() == 17;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert target.isLast();
    assert !target.isAfterLast();

    target.setIndex(18);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    Exception exception =
        Test.assertThrows(
                IndexOutOfBoundsException.class, "target.setIndex(-2)", () -> target.setIndex(-2))
            .get();
    assert exception.getMessage().equals("-2 (index must be between -1 and 18)");
    assert exception.getSuppressed().length == 0;

    exception =
        Test.assertThrows(
                IndexOutOfBoundsException.class, "target.setIndex(19)", () -> target.setIndex(19))
            .get();
    assert exception.getMessage().equals("19 (index must be between -1 and 18)");
    assert exception.getSuppressed().length == 0;
  }

  @TestMethod
  void testFirstMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.first();
    assert target.getIndex() == 0;
    assert target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();
  }

  @TestMethod
  void testBeforeFirstMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.first();
    assert target.getIndex() == 0;
    assert target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.beforeFirst();
    assert target.getIndex() == -1;
    assert !target.isFirst();
    assert target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();
  }

  @TestMethod
  void testLastMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.last();
    assert target.getIndex() == 17;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert target.isLast();
    assert !target.isAfterLast();
  }

  @TestMethod
  void testAfterLastMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.setIndex(2);
    assert target.getIndex() == 2;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.afterLast();
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();
  }

  @TestMethod
  void testPreviousMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.previous();
    assert target.getIndex() == 17;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert target.isLast();
    assert !target.isAfterLast();

    target.previous();
    assert target.getIndex() == 16;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();
  }

  @TestMethod
  void testNextMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.next();
    assert target.getIndex() == 18;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    target.beforeFirst();
    target.next();
    assert target.getIndex() == 0;
    assert target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.next();
    assert target.getIndex() == 1;
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();
  }

  @TestMethod
  void testDrawMethod() {
    SpriteSheet target = new SpriteSheet(image, 32, 32, 3, 6);
    Test.assertThrows(null, "target.draw(null)", () -> target.draw(null));

    target.first();
    Test.assertThrows(NullPointerException.class, "target.draw(null)", () -> target.draw(null));

    target.last();
    Test.assertThrows(NullPointerException.class, "target.draw(null)", () -> target.draw(null));

    target.afterLast();
    Test.assertThrows(null, "target.draw(null)", () -> target.draw(null));
  }
}
