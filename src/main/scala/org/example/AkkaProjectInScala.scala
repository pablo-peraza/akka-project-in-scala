package org.example

import akka.actor._
import akka.pattern.ask
import akka.util.duration._
import akka.util.Timeout
import akka.dispatch.{ExecutionContext, Await, Future}

case object Tick
case object Get


class Counter extends Actor {
  var count = 0

  def receive = {
    case Tick => count += 1
    case Get  => sender ! "Hola mundo!"
  }
}

object AkkaProjectInScala extends App {
  implicit val system = ActorSystem("Loquemeronque")

  val counter = system.actorOf(Props[Counter])

  counter ! Tick

  implicit val timeout = Timeout(5 seconds)
  val futuro: Future[Any] = counter ? Get
  val futuroMapeado: Future[String] = futuro.mapTo[String]
  val futuroX: Future[Int] = futuroMapeado.map[Int]{ a =>
    a.length
  }
  val temp = for(
    a <- futuroMapeado;
    b <- futuroX
  )yield(a,b)
  implicit val ec = ExecutionContext.defaultExecutionContext

  println("Antes de mi futuro")
  val mifuturo = Future{
    val temp = 10 + 10
    println(temp)
    temp
  }
  println("Despues de mi futuro")



  system.shutdown()
}
