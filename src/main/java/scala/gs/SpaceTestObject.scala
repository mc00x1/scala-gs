package scala.gs

import com.gigaspaces.annotation.pojo.SpaceClass
import java.util.logging.Logger
import com.gigaspaces.annotation.pojo.SpaceRouting
import scala.annotation.meta.beanGetter
import com.gigaspaces.annotation.pojo.SpaceId
import scala.beans.BeanProperty
import com.gigaspaces.annotation.pojo.SpaceProperty

@SpaceClass
class SpaceTestObject protected {

  val logger = Logger.getLogger(this.getClass().getName())

  @(SpaceRouting @beanGetter)
  @(SpaceId @beanGetter)
  @BeanProperty
  @SpaceProperty(nullValue="")
  protected var id: String = _
   
  def this(id: String) {
    this
    this.id = id
  }
}

object SpaceTestObject {
  def apply() = new SpaceTestObject
}
