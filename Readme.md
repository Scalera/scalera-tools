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

val two = Lazy(2)

require(!two.isEvaluated)
require(!two.value.isDefined)

val (v, newLazy) = two.eval

require(newLazy.isEvaluated)
require(newLazy.value == Some(2))
require(v == 2)
```

#### ```LazyS```

A stateful representation of a Lazy expression that uses scalaz ```State``` monad.
Example:

```scala
import org.scalera.tools._

val two = LazyS(2)

require(!two.isEvaluated)

val (newLazy, value) = (for {
  v <- eval[Int]
} yield v).apply(two)

require(newLazy.isEvaluated)
require(value == 2)
```

# About

Scalera staff