package org.example

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.pattern.ask
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.{ExecutionContext, Await, Future}

/**
 * Creado por<br/>
 *    Compañía:  Ciris Informatic Solutions<br/>
 *    Web:       www.ciriscr.com<br/>
 *    Usuario:   piousp<br/>
 *    Fecha:     13/12/12<br/>
 *    Hora:      03:27 PM<br/>
 * -----------------------------------------------
 * 
 */
case class HablarCon(a: ActorRef)
class Irene extends Actor {
  def receive = {
    case a: String =>
      println("[Irene] Recibí %s".format(a))
      sender ! "Hola, como está?"

    case HablarCon(actor) =>
      val tiempo = (math.random * 100).toLong
      println("[Irene] Voy a entablar negociaciones con mike en %s ms".format(tiempo))
      Thread.sleep(tiempo)
      actor ! "Hola"
  }
}

class Mike extends Actor{
  def receive = {
    case "Hola" =>
      println("[Mike] No quiero saludar")
      sender ! "No me joda"
    case _ =>
      println("[Mike] Ignorè lo que me dijeron")
  }
}

object Exe{
  def main(ar: Array[String]){
    val sistema = ActorSystem("rtyui")
    val irene = sistema.actorOf(Props[Irene])
    val mike = sistema.actorOf(Props[Mike])
//    implicit val timeout = new Timeout(1 seconds)
//    val futuroI = irene ? "Hola Irene"
//    val futuroM = (mike ? "Hola Mike").mapTo[String]
//
//    futuroI onComplete {
//      case Right(a) => a match{
//        case string: String => println("Irene me respondió %s".format(string))
//        case b => println("No sé que me respondió irene")
//      }
//      case Left(a) => imprimirError(a)
//    }
//
//    futuroM onComplete {
//      case Right(a) => println("Mike me respondió %s".format(a))
//      case Left(a) => imprimirError(a)
//    }

    irene ! HablarCon(mike)
    irene ! HablarCon(mike)
    irene ! HablarCon(mike)
    irene ! HablarCon(mike)
    Thread.sleep(2000)
    System.exit(0)
  }

  def imprimirError(a: Throwable){
    println("***********Error: %s".format(a))
  }
}