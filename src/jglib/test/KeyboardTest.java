package jglib.test;

import java.awt.Component;
import java.awt.event.KeyEvent;
import jglib.test.Test.TestClass;
import jglib.test.Test.TestMethod;
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
  void test() {
    Keyboard<TestKey> underTest = Keyboard.create(TestKey.values());

    assert !underTest.isPressed(TestKey.A);
    assert underTest.isReleased(TestKey.A);

    assert !underTest.isPressed(TestKey.B);
    assert underTest.isReleased(TestKey.B);

    assert !underTest.isPressed(TestKey.C);
    assert underTest.isReleased(TestKey.C);

    underTest.keyPressed(eventAt(TestKey.A));

    assert underTest.isPressed(TestKey.A);
    assert !underTest.isReleased(TestKey.A);

    assert !underTest.isPressed(TestKey.B);
    assert underTest.isReleased(TestKey.B);

    assert !underTest.isPressed(TestKey.C);
    assert underTest.isReleased(TestKey.C);

    underTest.keyReleased(eventAt(TestKey.A));
    underTest.keyPressed(eventAt(TestKey.C));

    assert !underTest.isPressed(TestKey.A);
    assert underTest.isReleased(TestKey.A);

    assert !underTest.isPressed(TestKey.B);
    assert underTest.isReleased(TestKey.B);

    assert underTest.isPressed(TestKey.C);
    assert !underTest.isReleased(TestKey.C);
  }

  private KeyEvent eventAt(TestKey key) {
    return new KeyEvent(new Component() {}, 0, 0, 0, key.code(), (char) key.code());
  }
}
