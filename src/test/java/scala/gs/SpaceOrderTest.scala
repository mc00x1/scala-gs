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
    val observer = new BlockingObserver(satisfactionFunc)
    (new Observable(sampleFunc)).subscribe(observer)
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

  private class BlockingObserver(f: (SpaceOrder) => Boolean) extends Observer(f) {
    var latch: CountDownLatch = new CountDownLatch(1) 

    override def onSubscribe {
      latch.await()
    }

    override def onComplete {
    	latch.countDown()
    }

  }
}