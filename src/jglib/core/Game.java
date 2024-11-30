package jglib.core;

import java.util.Optional;
import jglib.service.logging.GameLoggingService;

/**
 * 全てのゲームの抽象基底クラスです。
 *
 * @author mpp
 */
public abstract class Game {

  /**
   * ゲームを起動します。
   *
   * @param <T> ゲーム
   * @param gameClass ゲームの型
   * @return 起動したゲーム。起動に失敗した場合は空
   */
  public static <T extends Game> Optional<T> launch(Class<T> gameClass) {
    T game;

    try {
      game = gameClass.getDeclaredConstructor().newInstance();
      game.start();
    } catch (Exception e) {
      GameError error = new GameError(e);
      GameLoggingService.getLogger().error(error);
      game = null;
    }

    return Optional.ofNullable(game);
  }

  /** デフォルトコンストラクタです。 */
  protected Game() {
    Thread.currentThread().setUncaughtExceptionHandler(new UncaughtGameExceptionHandler());
  }

  /** このゲームの開始処理を行います。 */
  protected abstract void start();
}
