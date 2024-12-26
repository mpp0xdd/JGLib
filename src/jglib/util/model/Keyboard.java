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

  public void press(K key) {
    press(key.code());
  }

  public void release(K key) {
    release(key.code());
  }

  public boolean isPressed(K key) {
    return keyboard.get(key.code()) == Keystroke.PRESSED;
  }

  public boolean isReleased(K key) {
    return keyboard.get(key.code()) == Keystroke.RELEASED;
  }

  public KeyListener asKeyListener() {
    return new KeyListenerImpl();
  }

  private void press(int keyCode) {
    keyboard.replace(keyCode, Keystroke.PRESSED);
  }

  private void release(int keyCode) {
    keyboard.replace(keyCode, Keystroke.RELEASED);
  }

  private class KeyListenerImpl implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      press(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
      release(e.getKeyCode());
    }
  }
}
