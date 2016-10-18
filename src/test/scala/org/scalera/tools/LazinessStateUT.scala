package org.scalera.tools

import org.scalatest.{FlatSpec, Matchers}


class LazinessStateUT extends FlatSpec with Matchers {

  behavior of "LazinessState"

  it should "not be evaluate its state by initializing the lazy val" in {

    var sideEffectDetector: Int = 0

    val two = LazyS {
      sideEffectDetector += 1
      2
    }

    two.isEvaluated shouldEqual false

    sideEffectDetector shouldEqual 0

  }

  it should "generate a new Lazy instance when evaluating it" in {

    var sideEffectDetector: Int = 0

    val two = LazyS {
      sideEffectDetector += 1
      2
    }

    val (newTwo, evaluated) = (for {
      evaluated <- eval[Int]
    } yield evaluated).apply(two)

    sideEffectDetector shouldEqual 1
    newTwo.isEvaluated shouldEqual true
    evaluated shouldEqual 2

  }

  it should "evaluate once the expression within" in {

    var sideEffectDetector: Int = 0

    val two = LazyS {
      sideEffectDetector += 1
      2
    }

    sideEffectDetector shouldEqual 0

    val (_, (evaluated, evaluated2)) = (for {
      evaluated <- eval[Int]
      evaluated2 <- eval[Int]
    } yield (evaluated, evaluated2)).apply(two)

    sideEffectDetector shouldEqual 1
    evaluated shouldEqual 2

    sideEffectDetector shouldEqual 1
    evaluated2 shouldEqual 2

  }

}
