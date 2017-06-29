package swallow.master

/**
  * Created by zhouqihua on 2017/6/29.
  */

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import swallow.core.{KMDataType, KMFlow, KMFlowInfo, KMNode}

import scala.io.StdIn

object KMMaster {
  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load()
    val system = ActorSystem("masterActor", config.getConfig("masterActor").withFallback(config))
    val masterActor = system.actorOf(Props[KMMasterActor], name = "masterActor")

    val flowId: String = "FLOW-000001"
    val taskId: String = "TASK-000001"
    val master: String = "akka.tcp://masterActor@0.0.0.0:17200/user/masterActor"
    val from: String = "akka.tcp://localActor@0.0.0.0:17201/user/localActor"
    val to: String = "akka.tcp://remoteActor@0.0.0.0:17202/user/remoteActor"
    val content: String = "****** Hello Remote !!! ******"
    val description: String = "Version Beta 0.1"
    val dataType: KMDataType.DataType = KMDataType.FAKE

    val flowInfo = new KMFlowInfo(flowId, taskId, master, from, to, content, description, dataType)
    val flow = KMFlow.initWithFlowInfo(flowInfo)


    StdIn.readLine()

    KMJobDispatcher.dispatchNewFlow(system, flow)

    StdIn.readLine()
    system.terminate()
  }
}

class KMMaster {


}

object KMMasterActor {

}

class KMMasterActor extends KMNode {

}