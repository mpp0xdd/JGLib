package jglib.test;

import static jglib.test.Assertions.assertDoesNotThrow;
import static jglib.test.Assertions.assertThrows;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
import jglib.image.SpriteSheet;
import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;

@TestClass
class SpriteSheetTest {

  // Image for test
  private final int width = 192;
  private final int height = 96;
  private final int imageType = BufferedImage.TYPE_INT_RGB;
  private final BufferedImage image = new BufferedImage(width, height, imageType);

  private boolean equals(BufferedImage img1, BufferedImage img2) {
    // if (img1 == img2) return true;

    // If the width and height are different, it is false
    if (img1.getWidth() != img2.getWidth()) return false;
    if (img1.getHeight() != img2.getHeight()) return false;

    // If height and width are equal, check if all integer pixels are equal
    for (int y = 0; y < img1.getHeight(); y++)
      for (int x = 0; x < img1.getWidth(); x++)
        if (img1.getRGB(x, y) != img2.getRGB(x, y)) return false;
    return true;
  }

  SpriteSheetTest() {
    // Set each pixel of the image to a random RGB value
    Random rnd = new Random();
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        image.setRGB(x, y, (new Color(r, g, b)).getRGB());
      }
    }
  }

  @TestMethod
  void testCreateSpriteSheet() {
    assertDoesNotThrow(() -> SpriteSheet.create(image, 32, 32, 3, 6));
    assertDoesNotThrow(() -> SpriteSheet.create(image, 32, 32, 6, 3));

    assertThrows(NullPointerException.class, () -> SpriteSheet.create(null, 32, 32, 6, 3));

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> SpriteSheet.create(image, 0, 32, 6, 3));
    assert exception.getMessage().equals("0 (width value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (0*32*6*3) must be"
                + " equal");
    exception =
        assertThrows(IllegalArgumentException.class, () -> SpriteSheet.create(image, -1, 32, 6, 3));
    assert exception.getMessage().equals("-1 (width value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (-1*32*6*3) must be"
                + " equal");

    exception =
        assertThrows(IllegalArgumentException.class, () -> SpriteSheet.create(image, 32, 0, 6, 3));
    assert exception.getMessage().equals("0 (height value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*0*6*3) must be"
                + " equal");
    exception =
        assertThrows(IllegalArgumentException.class, () -> SpriteSheet.create(image, 32, -1, 6, 3));
    assert exception.getMessage().equals("-1 (height value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*-1*6*3) must be"
                + " equal");

    exception =
        assertThrows(IllegalArgumentException.class, () -> SpriteSheet.create(image, 32, 32, 0, 3));
    assert exception.getMessage().equals("0 (rows value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*0*3) must be"
                + " equal");
    exception =
        assertThrows(
            IllegalArgumentException.class, () -> SpriteSheet.create(image, 32, 32, -1, 3));
    assert exception.getMessage().equals("-1 (rows value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*-1*3) must be"
                + " equal");

    exception =
        assertThrows(IllegalArgumentException.class, () -> SpriteSheet.create(image, 32, 32, 6, 0));
    assert exception.getMessage().equals("0 (columns value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*6*0) must be"
                + " equal");
    exception =
        assertThrows(
            IllegalArgumentException.class, () -> SpriteSheet.create(image, 32, 32, 6, -1));
    assert exception.getMessage().equals("-1 (columns value must be a positive number)");
    assert exception.getSuppressed().length == 1;
    assert exception
        .getSuppressed()[0]
        .getMessage()
        .equals(
            "The size of the image (192*96) and the total size of the grid (32*32*6*-1) must be"
                + " equal");

    exception =
        assertThrows(
            IllegalArgumentException.class, () -> SpriteSheet.create(image, -1, -2, -3, -4));
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
    assert target.getIndex() == 18;
    assert target.getImage().isEmpty();
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

    assertThrows(NullPointerException.class, () -> target.setLocation(null));

    target.setIndex(-1);
    assert target.getIndex() == -1;
    assert target.getImage().isEmpty();
    assert !target.isFirst();
    assert target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.setIndex(0);
    assert target.getIndex() == 0;
    assert target.getImage().isPresent();
    assert equals(target.getImage().get(), image.getSubimage(0, 0, 32, 32));
    assert target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.setIndex(1);
    assert target.getIndex() == 1;
    assert target.getImage().isPresent();
    assert equals(target.getImage().get(), image.getSubimage(32, 0, 32, 32));
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert !target.isAfterLast();

    target.setIndex(17);
    assert target.getIndex() == 17;
    assert target.getImage().isPresent();
    assert equals(target.getImage().get(), image.getSubimage(32 * 5, 32 * 2, 32, 32));
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert target.isLast();
    assert !target.isAfterLast();

    target.setIndex(18);
    assert target.getIndex() == 18;
    assert target.getImage().isEmpty();
    assert !target.isFirst();
    assert !target.isBeforeFirst();
    assert !target.isLast();
    assert target.isAfterLast();

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> target.setIndex(-2));
    assert exception.getMessage().equals("-2 (index must be between -1 and 18)");
    assert exception.getSuppressed().length == 0;

    exception = assertThrows(IndexOutOfBoundsException.class, () -> target.setIndex(19));
    assert exception.getMessage().equals("19 (index must be between -1 and 18)");
    assert exception.getSuppressed().length == 0;
  }

  @TestMethod
  void testGetImageIndex() {
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);

    target.setIndex(5);
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> target.getImage(-2));
    assert exception.getMessage().equals("-2 (index must be between -1 and 18)");
    assert exception.getSuppressed().length == 0;
    exception = assertThrows(IndexOutOfBoundsException.class, () -> target.getImage(19));
    assert exception.getMessage().equals("19 (index must be between -1 and 18)");
    assert exception.getSuppressed().length == 0;
    assert target.getIndex() == 5;

    target.setIndex(3);
    assert target.getImage(-1).isEmpty();
    assert target.getImage(18).isEmpty();
    assert target.getIndex() == 3;

    target.setIndex(0);
    BufferedImage firstImage = target.getImage().get();
    target.setIndex(1);
    assert equals(firstImage, target.getImage(0).get());
    assert target.getIndex() == 1;
  }

  @TestMethod
  void testFirstMethod() {
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
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
    SpriteSheet target = SpriteSheet.create(image, 32, 32, 3, 6);
    assertDoesNotThrow(() -> target.draw(null));

    target.first();
    assertThrows(NullPointerException.class, () -> target.draw(null));

    target.last();
    assertThrows(NullPointerException.class, () -> target.draw(null));

    target.afterLast();
    assertDoesNotThrow(() -> target.draw(null));
  }
}
