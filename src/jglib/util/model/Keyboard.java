package jglib.util.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Keyboard<K extends Key> {

  @SafeVarargs
  public static <K extends Key> Keyboard<K> create(K... keys) {
    return new Keyboard<K>(keys);
  }

  private final Map<Integer, Keystroke> keyboard = new HashMap<>();

  private Keyboard(Key... keys) {
    for (Key key : keys) {
      keyboard.put(key.code(), Keystroke.RELEASED);
    }
  }

  public boolean press(K key) {
    return press(key.code()) == Keystroke.RELEASED;
  }

  public boolean release(K key) {
    return release(key.code()) == Keystroke.PRESSED;
  }

  public boolean isPressed(K key) {
    return keyboard.get(key.code()) == Keystroke.PRESSED;
  }

  public boolean isReleased(K key) {
    return keyboard.get(key.code()) == Keystroke.RELEASED;
  }

  public KeyListener asKeyPressListener() {
    return new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        press(e.getKeyCode());
      }
    };
  }

  public KeyListener asKeyListener() {
    return new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {
        release(e.getKeyCode());
      }

      @Override
      public void keyPressed(KeyEvent e) {
        press(e.getKeyCode());
      }
    };
  }

  private Keystroke press(int keyCode) {
    return keyboard.replace(keyCode, Keystroke.PRESSED);
  }

  private Keystroke release(int keyCode) {
    return keyboard.replace(keyCode, Keystroke.RELEASED);
  }
}
