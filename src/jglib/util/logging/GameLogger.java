package jglib.util.logging;

import jglib.base.GameThrowable;

public interface GameLogger {

  void warning(GameThrowable<?> gameThrowable);

  void error(GameThrowable<?> gameThrowable);
}