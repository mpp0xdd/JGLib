package jglib.test;

class AssertionErrorMessage<E, A> {

  public static <E, A> AssertionErrorMessage<E, A> of(E expected, A actual) {
    return new AssertionErrorMessage<E, A>(expected, actual);
  }

  private final E expected;
  private final A actual;

  private AssertionErrorMessage(E expected, A actual) {
    this.expected = expected;
    this.actual = actual;
  }

  public String message() {
    return String.format("<%s> expected, but got <%s>", expected, actual);
  }

  @Override
  public String toString() {
    return message();
  }
}
