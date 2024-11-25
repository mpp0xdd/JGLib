package jglib.util.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Keyboard<K extends Key> implements KeyListener {

  public static <K extends Key> Keyboard<K> create(Key... keys) {
    return new Keyboard<K>(keys);
  }

  private final Map<Integer, Keystroke> keyboard = new HashMap<>();

  private Keyboard(Key... keys) {
    for (Key key : keys) {
      keyboard.put(key.code(), Keystroke.RELEASED);
    }
  }

  public boolean isPressed(K key) {
    return keyboard.get(key.code()) == Keystroke.PRESSED;
  }

  public boolean isReleased(K key) {
    return keyboard.get(key.code()) == Keystroke.RELEASED;
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    keyboard.replace(e.getKeyCode(), Keystroke.PRESSED);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keyboard.replace(e.getKeyCode(), Keystroke.RELEASED);
  }
}
