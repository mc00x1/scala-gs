package scala.gs

import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.MustMatchers
import org.scalatest.BeforeAndAfterAll

@RunWith(classOf[JUnitRunner])
class SpaceOrderTest extends FunSpec with EmptySpace with MustMatchers with BeforeAndAfterAll {

  override protected def beforeAll() {
    gigaSpace
    container.start()
  }

  override protected def afterAll() {
    shutdown()
  }

  describe("An order") {
    it("Can be persisted to space") {
      var trade = new SpaceOrder("O1")
      gigaSpace.write(trade);
//      val read = gigaSpace.readIfExists(trade);
//      read must have('id("O1"))
      Thread.sleep(1000)
      val count = gigaSpace.count(new Object());
      val c = gigaSpace.read(new Object())
      count must be(1) 
    }
  }
}