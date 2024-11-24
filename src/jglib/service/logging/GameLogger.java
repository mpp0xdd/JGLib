package jglib.service.logging;

import jglib.core.GameThrowable;

public interface GameLogger {

  void warning(GameThrowable<?> gameThrowable);

  void error(GameThrowable<?> gameThrowable);
}
