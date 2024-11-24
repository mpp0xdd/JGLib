package jglib.component;

/**
 * 更新可能なゲーム画面の抽象基底クラスです。
 *
 * @author mpp
 */
abstract sealed class UpdatableGameScreenBase extends GameScreenBase
    implements UpdatableGameScreenRole permits GameScreen, GameScreenEx {

  /** ゲームループの実際の処理を行います。 */
  protected abstract void runGameLoop();
}
