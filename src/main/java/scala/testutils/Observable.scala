package scala.testutils;

class Observable[T] {
  private var observer: Observer[T] = _
  private var probe: () => T = null

  def this(f: () => T) {
    this
    this.probe = f
  }

  private def sample() = {
    val timeout = new Timeout(5000);
    var o: Option[T] = Option(this.probe())
    while (o.isEmpty) {
      if (timeout.hasTimeout()) {
        throw new AssertionError();
      }
      Thread.sleep(1000);
      o = Option(this.probe());
    }
    observer.onNext(o.get)
  }

  def subscribe(observer: Observer[T]) {
    this.observer = observer
    new Thread(new Runnable() {
      def run() {
        sample()
      }
    }).start()
    observer.onSubscribe
  }

}
