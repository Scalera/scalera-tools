# Scalera-tools

A bunch of fresh scala tools.

## Components

### Lazy values

Two different ways of achieving explicitly typed laziness are provided:

#### ```Lazy```

A simple way that allows expressing a full lazy block.
Example:

```scala
import org.scalera.tools._
import Laziness._

val two = Lazy(2)

require(!two.isEvaluated)

val (v, newLazy) = two.eval

require(newLazy.isEvaluated)
require(v == 2)
```

#### ```LazyS```

A stateful representation of a Lazy expression that uses scalaz ```State``` monad.
Example:

```scala
import org.scalera.tools._
import LazinessState._

val two = LazyS(2)

require(!two.isEvaluated)

val (newLazy, value) = (for {
  v <- eval[Int]
} yield v).apply(two)

require(newLazy.isEvaluated)
require(value == 2)
```

### Traverse operations

In an additional effort, extra features (that people might thought that std Scala collections API lacked) are provided here.

#### Map operations

As [@pfcoperez](https://github.com/pfcoperez) proposed, join methods for scala Maps have been added: fullJoin, innerJoin, leftJoin and rightJoin.

```scala
import org.scalera.tools.TraverseFunctions._

val m1: Map[Int, String] = ???
val m2: Map[Int, Double] = ???

//  Full join

m1 fullJoin m2
m1 <+> m2

//  Inner join

m1 innerJoin m2
m1 >+< m2

//  Left join

m1 leftJoin m2
m1 <+ m2

//  Right join

m1 rightJoin m2
m1 +> m2
```

Merge operations have been added too. 
There is a simple merge when both maps have same value types and a more generic merge method that uses auxiliary functions for converting both map values into some common type before merging.  
You can use them as follows:

```scala
//  Merge
import org.scalera.tools.TraverseFunctions._

//  Simple merge : concat String values

val m1: Map[Int, String] = ???
val m3: Map[Int, String] = ???

m1.merge(m3, _ + _)

//  Generic merge : Convert Double to String and then concat map values

val m2: Map[Int, Double] = ???

m1.gMerge[Double,String](m2, identity, _.toString(), _ + _)
```

For getting a further idea, please check ```TraverseFunctionsUT``` test out.

# About

Scalera staff