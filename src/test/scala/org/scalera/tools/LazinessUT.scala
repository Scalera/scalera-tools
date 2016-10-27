package org.scalera.tools

import org.scalatest.{FlatSpec, Matchers}
import Laziness._

class LazinessUT extends FlatSpec with Matchers {

  behavior of "Laziness"

  it should "not be evaluate its state by initializing the lazy val" in {

    var sideEffectDetector: Int = 0

    val two = Lazy {
      sideEffectDetector += 1
      2
    }

    sideEffectDetector shouldEqual 0

  }

  it should "generate a new Lazy instance when evaluating it" in {

    var sideEffectDetector: Int = 0

    val two = Lazy {
      sideEffectDetector += 1
      2
    }

    val (evaluated, newLazy) = two.eval

    sideEffectDetector shouldEqual 1
    evaluated shouldEqual 2

  }

  it should "evaluate once the expression within" in {

    var sideEffectDetector: Int = 0

    val two = Lazy {
      sideEffectDetector += 1
      2
    }

    val (evaluated, newLazy) = two.eval

    sideEffectDetector shouldEqual 1
    evaluated shouldEqual 2

    val (evaluated2, newLazy2) = newLazy.eval

    sideEffectDetector shouldEqual 1
    evaluated2 shouldEqual 2
    newLazy2 shouldEqual newLazy

  }

}
