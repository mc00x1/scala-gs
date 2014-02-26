package scala.testutils;

import scala.annotation.meta.beanGetter
import scala.beans.BeanProperty

class Observer[T] {
  
  private var decideFunc: (T) => Boolean = null
  private var _isProcessed: Boolean = false

  def this(f: (T) => Boolean) {
    this
    this.decideFunc = f
  }

  def onNext( t: T) = {
	 val p= this.decideFunc( t) 
	 if (p) {
	   _isProcessed = true
	   this.onComplete
	 }
  }
  
  def onSubscribe = {}

  def onComplete = {}

  def isProcessed = _isProcessed
  
  def isProcessed_= (value: Boolean): Unit = _isProcessed = value
}
