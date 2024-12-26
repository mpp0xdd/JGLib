package jglib.test;

import static jglib.test.Assertions.assertFalse;
import static jglib.test.Assertions.assertTrue;
import java.awt.Component;
import java.awt.event.KeyEvent;
import jglib.test.Tests.TestClass;
import jglib.test.Tests.TestMethod;
import jglib.util.model.Key;
import jglib.util.model.Keyboard;

@TestClass
class KeyboardTest {

  enum TestKey implements Key {
    A(0),
    B(1),
    C(2);

    final int code;

    TestKey(int code) {
      this.code = code;
    }

    @Override
    public int code() {
      return code;
    }
  }

  @TestMethod
  void testPressAndRelease() {
    Keyboard<TestKey> underTest = Keyboard.create(TestKey.values());

    assertFalse(underTest.isPressed(TestKey.A));
    assertTrue(underTest.isReleased(TestKey.A));

    assertFalse(underTest.isPressed(TestKey.B));
    assertTrue(underTest.isReleased(TestKey.B));

    assertFalse(underTest.isPressed(TestKey.C));
    assertTrue(underTest.isReleased(TestKey.C));

    underTest.press(TestKey.A);

    assertTrue(underTest.isPressed(TestKey.A));
    assertFalse(underTest.isReleased(TestKey.A));

    assertFalse(underTest.isPressed(TestKey.B));
    assertTrue(underTest.isReleased(TestKey.B));

    assertFalse(underTest.isPressed(TestKey.C));
    assertTrue(underTest.isReleased(TestKey.C));

    underTest.release(TestKey.A);
    underTest.press(TestKey.C);

    assertFalse(underTest.isPressed(TestKey.A));
    assertTrue(underTest.isReleased(TestKey.A));

    assertFalse(underTest.isPressed(TestKey.B));
    assertTrue(underTest.isReleased(TestKey.B));

    assertTrue(underTest.isPressed(TestKey.C));
    assertFalse(underTest.isReleased(TestKey.C));
  }

  @TestMethod
  void testKeyListener() {
    Keyboard<TestKey> underTest = Keyboard.create(TestKey.values());

    assertFalse(underTest.isPressed(TestKey.A));
    assertTrue(underTest.isReleased(TestKey.A));

    assertFalse(underTest.isPressed(TestKey.B));
    assertTrue(underTest.isReleased(TestKey.B));

    assertFalse(underTest.isPressed(TestKey.C));
    assertTrue(underTest.isReleased(TestKey.C));

    underTest.asKeyListener().keyPressed(eventAt(TestKey.A));

    assertTrue(underTest.isPressed(TestKey.A));
    assertFalse(underTest.isReleased(TestKey.A));

    assertFalse(underTest.isPressed(TestKey.B));
    assertTrue(underTest.isReleased(TestKey.B));

    assertFalse(underTest.isPressed(TestKey.C));
    assertTrue(underTest.isReleased(TestKey.C));

    underTest.asKeyListener().keyReleased(eventAt(TestKey.A));
    underTest.asKeyListener().keyPressed(eventAt(TestKey.C));

    assertFalse(underTest.isPressed(TestKey.A));
    assertTrue(underTest.isReleased(TestKey.A));

    assertFalse(underTest.isPressed(TestKey.B));
    assertTrue(underTest.isReleased(TestKey.B));

    assertTrue(underTest.isPressed(TestKey.C));
    assertFalse(underTest.isReleased(TestKey.C));
  }

  private KeyEvent eventAt(TestKey key) {
    return new KeyEvent(new Component() {}, 0, 0, 0, key.code(), (char) key.code());
  }
}
