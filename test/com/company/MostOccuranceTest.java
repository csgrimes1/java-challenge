package com.company;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Created by grimescs on 9/26/16.
 */
public class MostOccuranceTest {
    @Test
    public void mostOccurring() {
        Integer[] ar = new Integer[]{2, 1, 3, 5, 4, 3, 2, 0, 2, 3, 3};
        final Optional<Integer> result = MostOccurance.mostOccurring(ar);
        final int expect = 3;

        Assert.assertEquals((int)expect, (int)result.get());
    }

    @Test
    public void mostOccurring2() {
        Integer[] ar = new Integer[]{2, 1, 3, 5, 4, 3, 2, 0, 2, 3, 3};
        final Optional<Integer> result = MostOccurance.mostOccurring2(ar, HashMap<Integer, Long>::new);
        final int expect = 3;

        Assert.assertEquals((int)expect, (int)result.get());
    }


    static IntStream getStream () {
        IntSupplier generator = new IntSupplier() {
            int current = 0;

            public int getAsInt() {
                return (current++) % 19 % 13 + 20;
            }
        };

        return IntStream.generate(generator).limit(1000000);
    }

    static Integer[] _testArray = (Integer[])getStream().boxed().toArray(Integer[]::new);

    @Test
    public void timedTests() {
        timedTest(HashMap<Integer, Long>::new, "HashMap");
        timedTest(Hashtable<Integer, Long>::new, "Hashtable");
        timedTest(LinkedHashMap<Integer, Long>::new, "LinkedHashMap");
        timedTest(TreeMap<Integer, Long>::new, "TreeMap");
    }

    void timedTest(Supplier<Map<Integer, Long>> mapSupplier, String mapType) {
        for(int n=0; n<2; n++) {
            //streams-based
            final Date ds1 = new Date();
            long ls = MostOccurance.mostOccurring(_testArray).get().longValue();
            final Date ds2 = new Date();

            //traditional Java solution
            final Date dt1 = new Date();
            long lt = MostOccurance.mostOccurring2(_testArray, mapSupplier).get().longValue();
            final Date dt2 = new Date();

            long deltaS = ds2.getTime() - ds1.getTime(),
                    deltaT = dt2.getTime() - dt1.getTime();

            System.out.println("functional solution time: " + deltaS + "ms; traditional solution ("
                    + mapType + "): " + deltaT + "ms; value=" + lt);
            //The functional solution outperforms the mutable one!
            Assert.assertEquals(ls, lt);
        }
    }
}
