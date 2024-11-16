package jglib.component;

import java.awt.Component;

public sealed interface GameScreenRole permits GameScreen, GameScreenEx {

  /**
   * ゲーム画面のサイズを変更します。
   *
   * @param width ゲーム画面の横幅
   * @param height ゲーム画面の縦幅
   */
  void setScreenSize(int width, int height);

  /**
   * ゲームループを開始します。<br>
   * このメソッドによって開始されたゲームループを終了したい場合は，{@code stopGameLoop()}を呼び出してください。
   *
   * @throws IllegalStateException ゲームループが既に開始されている場合
   * @see #stopGameLoop()
   */
  void startGameLoop();

  /**
   * ゲームループを停止します。<br>
   * このメソッドによって停止されたゲームループを再び開始したい場合は，{@code startGameLoop()}を呼び出してください。
   *
   * @throws IllegalStateException ゲームループが既に停止している場合
   * @see #startGameLoop()
   */
  void stopGameLoop();

  default Component asComponent() {
    return (Component) this;
  }
}
