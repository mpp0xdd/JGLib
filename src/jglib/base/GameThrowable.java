package jglib.base;

public interface GameThrowable<T extends Throwable & GameThrowable<T>> {

  default Throwable asThrowable() {
    return (Throwable) this;
  }
}
