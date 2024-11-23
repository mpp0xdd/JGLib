package jglib.component;

public sealed interface UpdatableGameScreenRole extends GameScreenRole
    permits GameScreenBase, GameScreen, GameScreenEx {
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
}
