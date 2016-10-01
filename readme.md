### Challenge

Find the most occurring member of an array. For example,

```[2, 2, 3, 1, 5, 1, 9, 2]```

... must yield 2 as a result.

The traditional approach is to build a `Map<Integer, Long>` while iterating, with the
keys being the array items and the values being the associated count. At each item,
check if it has the greatest count thus far and store it. Conventional wisdom is that
this is the fastest solution in Java.

There is another approach using `java.util.stream` and a more functional approach. In
this solution, you use a `groupBy` operation on the elements, resulting in a map. Then
you do a reduce operation on the map to yield the pair containing the highest count. The
key of that pair is the result.

The functional approach consistently outperforms the traditional one, trying the traditional
approach using (4) different implementations of Map<K, V> (HashMap, Hashtable, LinkedHashMap,
and TreeMap).

The results:

```
functional solution time: 44ms; traditional solution (HashMap): 53ms; value=20
functional solution time: 19ms; traditional solution (HashMap): 21ms; value=20
functional solution time: 22ms; traditional solution (Hashtable): 47ms; value=20
functional solution time: 24ms; traditional solution (Hashtable): 39ms; value=20
functional solution time: 31ms; traditional solution (LinkedHashMap): 41ms; value=20
functional solution time: 23ms; traditional solution (LinkedHashMap): 28ms; value=20
functional solution time: 30ms; traditional solution (TreeMap): 29ms; value=20
functional solution time: 22ms; traditional solution (TreeMap): 32ms; value=20
```

Each Map<> implementation runs twice to account for loading times. The functional solution consistently
outperforms the traditional solution.
