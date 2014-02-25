package scala.gs

import org.openspaces.core.space.UrlSpaceConfigurer
import org.openspaces.core.GigaSpaceConfigurer
import org.openspaces.core.GigaSpace
import org.openspaces.events.polling.SimplePollingContainerConfigurer
import org.openspaces.events.polling.SimplePollingEventListenerContainer
import org.openspaces.core.transaction.manager.DistributedJiniTxManagerConfigurer
import org.openspaces.events.polling.receive.ExclusiveReadReceiveOperationHandler
import org.springframework.transaction.PlatformTransactionManager
import org.openspaces.events.polling.receive.AbstractFifoGroupingReceiveOperationHandler
import org.openspaces.events.SpaceDataEventListener
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler

trait Space {

  private lazy val iSpaceConfigurer = new UrlSpaceConfigurer("/./mc-space")
  val transactionManager: PlatformTransactionManager = new DistributedJiniTxManagerConfigurer().transactionManager()
  private lazy val gigaSpaceConfigurer = new GigaSpaceConfigurer(iSpaceConfigurer)
  private lazy val containerConfigurer = new SimplePollingContainerConfigurer(gigaSpace);

  lazy val gigaSpace: GigaSpace = {
    gigaSpaceConfigurer.transactionManager(transactionManager).gigaSpace()
  }

  lazy val container: SimplePollingEventListenerContainer = {
    val receiveOperationHandler: AbstractFifoGroupingReceiveOperationHandler = new SingleTakeReceiveOperationHandler()
    val eventListener: SimpleEventListener = new SimpleEventListener()
    receiveOperationHandler.setUseFifoGrouping(true)
    var template: SpaceOrder = SpaceOrder.apply
    containerConfigurer
      .template(template)
      .transactionManager(transactionManager)
      .receiveOperationHandler(receiveOperationHandler)
      .eventListener(eventListener)
      .pollingContainer()
  }

  def shutdown(): Unit = {
    iSpaceConfigurer.destroy()
  }

}
