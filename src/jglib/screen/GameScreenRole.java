package jglib.screen;

import java.awt.Component;

/**
 * ゲーム画面の役割を表します。
 *
 * @author mpp
 */
public sealed interface GameScreenRole permits UpdatableGameScreenRole, GameScreenBase {

  /**
   * ゲーム画面のサイズを変更します。
   *
   * @param width ゲーム画面の横幅
   * @param height ゲーム画面の縦幅
   */
  void setScreenSize(int width, int height);

  /**
   * ゲーム画面を {@link Component} として扱えるようにします。
   *
   * @return Component
   */
  default Component asComponent() {
    return (Component) this;
  }
}
