package scala.gs

import com.gigaspaces.annotation.pojo.SpaceClass
import java.util.logging.Logger
import com.gigaspaces.annotation.pojo.SpaceRouting
import scala.annotation.meta.beanGetter
import com.gigaspaces.annotation.pojo.SpaceProperty
import com.gigaspaces.annotation.pojo.SpaceFifoGroupingProperty
import com.gigaspaces.annotation.pojo.SpaceId
import scala.beans.BeanProperty

@SpaceClass
class SpaceOrder protected {

  val logger = Logger.getLogger(this.getClass().getName())

  @(SpaceRouting @beanGetter)
  @(SpaceId @beanGetter)
  @BeanProperty
  @SpaceProperty
  @(SpaceFifoGroupingProperty @beanGetter)
  protected var id: String = _

  @(SpaceProperty @beanGetter)(nullValue = "-1")
  @BeanProperty
  protected var processed: Long = _ 

  def this(id: String) {
    this
    this.id = id
  }
}

object SpaceOrder {
  def apply() : SpaceOrder = { 
    val result : SpaceOrder = new SpaceOrder
    result.setProcessed(-1)
    return result
  }
}