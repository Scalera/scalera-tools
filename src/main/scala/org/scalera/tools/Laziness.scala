package org.scalera.tools

object Laziness {

  trait Lazy[T] { lzy =>

    protected lazy val evalF : () => T = ???

    protected val value: Option[T] = None

    def eval: (T, Lazy[T]) = {
      val evaluated = evalF.apply()
      evaluated -> new Lazy[T] { mutated =>
        override protected lazy val evalF = lzy.evalF
        override protected val value = Some(evaluated)
        override def eval: (T, Lazy[T]) = evaluated -> mutated
      }
    }

    def isEvaluated: Boolean = value.isDefined

  }

  object Lazy {

    def apply[T](f: => T): Lazy[T] =
      new Lazy[T]{ override protected lazy val evalF = () => f }

  }
}
