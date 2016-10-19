package org.scalera.tools

import scalaz.State

object LazinessState {

  type LazyS[T] = (() => T, Option[T])

  def LazyS[T](f: => T) = (() => f, None)

  def eval[T] = State[LazyS[T], T]{
    case ((f, None)) => {
      val evaluated = f.apply()
      ((f, Some(evaluated)), evaluated)
    }
    case s@((_, r@Some(evaluated))) => (s, evaluated)
  }

  implicit class LazySHelper[T](lzy: LazyS[T]){
    def isEvaluated: Boolean = lzy._2.isDefined
  }

}
