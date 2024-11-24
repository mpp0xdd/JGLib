package jglib.core;

public interface GameThrowable<T extends Throwable & GameThrowable<T>> {

  default Throwable asThrowable() {
    return (Throwable) this;
  }
}
