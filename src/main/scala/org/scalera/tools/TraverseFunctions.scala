package org.scalera.tools

object TraverseFunctions {

  implicit class MapHelpers[K,V](m1: Map[K,V]){

    //  Joining

    def fullJoin[W](m2: Map[K,W]): Map[K,(Option[V],Option[W])] =
      (m1.keySet ++ m2.keySet).map{ k =>
        k -> (m1.get(k),m2.get(k))
      }.toMap

    def <+>[W](m2: Map[K,W]): Map[K,(Option[V],Option[W])] = fullJoin(m2)

    def innerJoin[W](m2: Map[K,W]): Map[K,(V,W)] =
      (m1.keySet intersect m2.keySet).map{ k =>
        k -> (m1(k),m2(k))
      }.toMap

    def >+<[W](m2: Map[K,W]): Map[K,(V,W)] = innerJoin(m2)

    def leftJoin[W](m2: Map[K,W]): Map[K,(V,Option[W])] =
      m1.map{
        case (k,v) =>  k -> (v,m2.get(k))
      }

    def <+[W](m2: Map[K,W]): Map[K,(V,Option[W])] = leftJoin(m2)

    def rightJoin[W](m2: Map[K,W]): Map[K,(Option[V],W)] =
      m2.map{
        case (k,v) =>  k -> (m1.get(k), v)
      }

    def +>[W](m2: Map[K,W]): Map[K,(Option[V],W)] = rightJoin(m2)

    //  Merging

    /**
      * Generic merge.
      * It uses auxiliary functions for converting both maps values
      * into some common type, before merging.
      */
    def gMerge[W,X](
      m2: Map[K, W],
      f1: V => X,
      f2: W => X,
      m: (X,X) => X): Map[K,X] =
      (m1.keySet ++ m2.keySet).map{ k =>
        k -> ((m1.get(k), m2.get(k)) match {
          case (Some(v), None) => f1(v)
          case (None, Some(w)) => f2(w)
          case (Some(v), Some(w)) => m(f1(v),f2(w))
        })
      }.toMap

    def merge(m2: Map[K,V], f: (V,V) => V): Map[K,V] =
      gMerge[V,V](m2, f1 = identity, f2 = identity, m = f)

  }

}
