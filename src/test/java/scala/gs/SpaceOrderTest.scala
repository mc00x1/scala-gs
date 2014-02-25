package scala.gs

import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.matchers.MustMatchers
import org.scalatest.BeforeAndAfterAll
import com.gigaspaces.annotation.pojo.SpaceClass
import scala.testutils.Probe
import org.scalatest.junit.JUnitRunner
import scala.testutils.Poller
import scala.testutils.Observer
import scala.testutils.Observable
import org.openspaces.scala.core.ScalaGigaSpacesImplicits._
import com.j_spaces.core.client.SQLQuery
import java.util.Arrays
import java.util.concurrent.CountDownLatch

@RunWith(classOf[JUnitRunner])
class SpaceOrderTest extends FunSpec with Space with MustMatchers with BeforeAndAfterAll {

  override protected def beforeAll() {
    gigaSpace
    container.start()
  }

  override protected def afterAll() {
    shutdown()
  }

  protected def assertProcessed(sampleFunc: () => SpaceOrder, satisfactionFunc: SpaceOrder => Boolean) {
<<<<<<< HEAD
    val observer = new BlockingObserver(satisfactionFunc)
    (new Observable(sampleFunc)).subscribe(observer)
=======
    val latch: CountDownLatch = new CountDownLatch(1)
    val observer = new TestObserver(satisfactionFunc, latch)
    (new Observable(sampleFunc)).subscribe(observer)
    latch.await()
>>>>>>> 4ebdfd984b45f643f8e0f873c6b2813f60800b4c
    observer.isProcessed must be(true)
  }

  describe("An order") {
    it("Is processed by the polling container") {
      var order = new SpaceOrder("O1")
      gigaSpace.write(order);

      def sampleFunc: () => SpaceOrder = { () => gigaSpace.read(new SQLQuery(classOf[SpaceOrder], "processed > 0", "")) }
      def satisfactionFunc: (SpaceOrder) => Boolean = { (s: SpaceOrder) => { s.getProcessed > 0 } }

      assertProcessed(sampleFunc, satisfactionFunc)
    }
  }

<<<<<<< HEAD
  private class BlockingObserver(f: (SpaceOrder) => Boolean) extends Observer(f) {
    var latch: CountDownLatch = new CountDownLatch(1) 

    override def onSubscribe {
      latch.await()
=======
  private class TestObserver(f: (SpaceOrder) => Boolean) extends Observer(f) {
    var latch: CountDownLatch = null

    def this(f: (SpaceOrder) => Boolean, latch: CountDownLatch) {
      this(f)
      this.latch = latch
>>>>>>> 4ebdfd984b45f643f8e0f873c6b2813f60800b4c
    }

    override def onComplete {
    	latch.countDown()
    }

  }
}