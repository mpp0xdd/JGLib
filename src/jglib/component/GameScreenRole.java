package jglib.component;

import java.awt.Component;

public sealed interface GameScreenRole permits GameScreen, GameScreenEx {

  void setScreenSize(int width, int height);

  void startGameLoop();

  void stopGameLoop();

  default Component asComponent() {
    return (Component) this;
  }
}
