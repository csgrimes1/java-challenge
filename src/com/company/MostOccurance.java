package com.company;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by grimescs on 9/26/16.
 */
public class MostOccurance {

    //Functional solution
    public static <T> Optional<T> mostOccurring (Stream<T> collection) {
        final Map<T, Long> counts = collection.collect(
                Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )
        );
        final Optional<Map.Entry<T, Long>> minEntry = counts.entrySet().stream().reduce(
            (accum, item) -> item.getValue() > accum.getValue() ? item : accum
        );

        return minEntry.isPresent() ? Optional.of(minEntry.get().getKey()) : Optional.empty();
    }

    public static <T> Optional<T> mostOccurring(T[] ar) {
        return mostOccurring(Arrays.asList(ar).stream());
    }


    //Traditional solution
    public static <T> Optional<T> mostOccurring2 (T[] collection, Supplier<Map<T, Long>> mapFactory) {
        Map<T, Long> counts = mapFactory.get();
        Optional<T> maxItem = Optional.empty();
        long maxCount = 0L;

        for(T item: collection) {
            final Long count = counts.putIfAbsent(item, 0L);
            if(null != count) {
                final Long newCount = count + 1;
                counts.put(item, newCount);
                if (newCount > maxCount) {
                    maxCount = newCount;
                    maxItem = Optional.of(item);
                }
            }
        }

        return maxItem;
    }
}
