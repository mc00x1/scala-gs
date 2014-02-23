package scala.gs

import org.openspaces.core.GigaSpace
import org.springframework.transaction.TransactionStatus
import org.openspaces.events.SpaceDataEventListener

class SimpleEventListener extends SpaceDataEventListener[SpaceOrder] {

  override def onEvent(data: SpaceOrder, gigaSpace: GigaSpace, txStatus: TransactionStatus, source: Object) {
    data.setProcessed(System.currentTimeMillis())
    gigaSpace.write(data)
    txStatus.flush()
  }

}