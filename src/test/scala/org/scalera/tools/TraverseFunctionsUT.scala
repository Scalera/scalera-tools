package org.scalera.tools

import org.scalatest.{FlatSpec, Matchers}

class TraverseFunctionsUT extends FlatSpec with Matchers {

  behavior of "TraverseFunctions"

  it should "Map - perform full joins" in {
    import TraverseFunctions._

    val m1 = Map(1 -> "red", 2 -> "green", 3 -> "blue")
    val m2 = Map(2 -> 45, 3 -> 28, 4 -> 123, 5 -> 456)

    val fullOuterJoined = Map(
      1 -> (Option("red"), None),
      2 -> (Option("green"),Option(45)),
      3 -> (Option("blue"),Option(28)),
      4 -> (None, Option(123)),
      5 -> (None, Option(456)))

    (m1 fullJoin m2) shouldEqual fullOuterJoined
    (m1 <+> m2) shouldEqual fullOuterJoined
  }

  it should "Map - perform inner joins" in {
    import TraverseFunctions._

    val m1 = Map(1 -> "red", 2 -> "green", 3 -> "blue")
    val m2 = Map(2 -> 45, 3 -> 28, 4 -> 123, 5 -> 456)

    val innerJoined = Map(
      2 -> ("green",45),
      3 -> ("blue",28))

    (m1 innerJoin m2) shouldEqual innerJoined
    (m1 >+< m2) shouldEqual innerJoined
  }

  it should "Map - perform left joins" in {
    import TraverseFunctions._

    val m1 = Map(1 -> "red", 2 -> "green", 3 -> "blue")
    val m2 = Map(2 -> 45, 3 -> 28, 4 -> 123, 5 -> 456)

    val leftJoined = Map(
      1 -> ("red", None),
      2 -> ("green", Some(45)),
      3 -> ("blue", Some(28)))

    (m1 leftJoin m2) shouldEqual leftJoined
    (m1 <+ m2) shouldEqual leftJoined
  }

  it should "Map - perform right joins" in {
    import TraverseFunctions._

    val m1 = Map(1 -> "red", 2 -> "green", 3 -> "blue")
    val m2 = Map(2 -> 45, 3 -> 28, 4 -> 123, 5 -> 456)

    val rightJoined = Map(
      2 -> (Option("green"), 45),
      3 -> (Option("blue"), 28),
      4 -> (None, 123),
      5 -> (None, 456))

    (m1 rightJoin m2) shouldEqual rightJoined
    (m1 +> m2) shouldEqual rightJoined
  }

  it should "Map - perform generic merge operations" in {
    import TraverseFunctions._
    val m1 = Map(1 -> "red", 2 -> "green", 3 -> "blue")
    val m2 = Map(2 -> 45, 3 -> 28, 4 -> 123, 5 -> 456)

    val merged = Map(
      1 -> "red",
      2 -> "green45",
      3 -> "blue28",
      4 -> "123",
      5 -> "456")

    m1.gMerge[Int,String](m2, identity, _.toString, _ + _) shouldEqual merged

  }

  it should "Map - perform simple merge operations" in {
    import TraverseFunctions._
    val m1 = Map(1 -> "red", 2 -> "green", 3 -> "blue")
    val m2 = Map(2 -> "45", 3 -> "28", 4 -> "123", 5 -> "456")

    val merged = Map(
      1 -> "red",
      2 -> "green45",
      3 -> "blue28",
      4 -> "123",
      5 -> "456")

    m1.merge(m2, _ + _) shouldEqual merged

  }

}
