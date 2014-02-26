package scala.testutils;

class Poller[T] {
  protected var timeout: Long = _
  protected var interval: Long = _

  def this(timeout: Long, pollInterval: Long) {
    this
    this.timeout = timeout
    this.interval = pollInterval
  }

  def check(probe: Probe[T]) {
    val timeout = new Timeout(this.timeout);
    probe.sample;
    while (!probe.isSatisfied()) {
      if (timeout.hasTimeout()) {
        throw new AssertionError();
      }
      Thread.sleep(this.interval);
      probe.sample();
    }

  }

}