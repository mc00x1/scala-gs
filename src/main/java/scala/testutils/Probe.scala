package scala.testutils

class Probe[T] {

  var probe: () => T = null

  def this(f: () => T) {
    this
    this.probe = f
  }

  def sample(): Observable[T] = {
    var result: T = this.probe()
    return new Observable()
  }

  def isSatisfied(): Boolean = {
    false
  }

}
