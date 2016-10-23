package shared.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapUtils {

    /**
     * Get every key in {@code items} where the entry satisfies {@code testFunction}.
     *
     * @param items        the map to work with, not null
     * @param testFunction the predicate to test entries against, not null
     * @param <K>          the key of the map
     * @param <V>          the value of the map
     * @return a set of all keys where the entry satisfies {@code testFunction}
     */
    @NotNull
    public static <K, V> Set<K> keysWithEntryMatching(@NotNull Map<K, V> items,
                                                      @NotNull Predicate<Map.Entry<K, V>> testFunction) {
        // value or any contained value in items may be null
        return items.entrySet().stream()
                .filter(testFunction)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Get every value in {@code items} where the entry satisfies {@code testFunction}.
     *
     * @param items        the map to work with, not null
     * @param testFunction the predicate to test entries against, not null
     * @param <K>          the key of the map
     * @param <V>          the value of the map
     * @return a set of all keys where the entry satisfies {@code testFunction}
     */
    @NotNull
    public static <K, V> Set<V> valuesWithEntryMatching(@NotNull Map<K, V> items,
                                                        @NotNull Predicate<Map.Entry<K, V>> testFunction) {
        // value or any contained value in items may be null
        return items.entrySet().stream()
                .filter(testFunction)
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
    }

    /**
     * Get every key in {@code items} where the value satisfies {@code testFunction}.
     *
     * @param items        the map to work with, not null
     * @param testFunction the predicate to test values against, not null
     * @param <K>          the key of the map
     * @param <V>          the value of the map
     * @return a set of all keys where the value satisfies {@code testFunction}
     */
    @NotNull
    public static <K, V> Set<K> keysWithValueMatching(@NotNull Map<K, V> items,
                                                      @NotNull Predicate<V> testFunction) {
        // value or any contained value in items may be null
        return keysWithEntryMatching(items, e -> testFunction.test(e.getValue()));
    }

    /**
     * Get every key in {@code items} where the value equals {@code value}.
     *
     * @param items the map to work with, not null
     * @param value the value to check equality against, may be null
     * @param <K>   the key of the map
     * @param <V>   the value of the map
     * @return a set of all keys where the value equals {@code value}
     */
    public static <K, V> Set<K> keysWithValue(@NotNull Map<K, V> items, @Nullable V value) {
        // value or any contained value in items may be null
        return keysWithEntryMatching(items, e -> Objects.equals(e.getValue(), value));
    }

    /**
     * Get every value in {@code items} that satisfies {@code testFunction}.
     *
     * @param items        the map to work with, not null
     * @param testFunction the predicate to test values against, not null
     * @param <K>          the key of the map
     * @param <V>          the value of the map
     * @return a set of all values that satisfy {@code testFunction}
     */
    public static <K, V> Set<V> valuesMatching(Map<K, V> items,
                                               Predicate<? super V> testFunction) {
        // value or any contained value in items may be null
        return items.values()
                .stream()
                .filter(testFunction)
                .collect(Collectors.toSet());
    }

    /**
     * Get every value in {@code items} where the key satisfies {@code testFunction}.
     *
     * @param items        the map to work with, not null
     * @param testFunction the predicate to test keys against, not null
     * @param <K>          the key of the map
     * @param <V>          the value of the map
     * @return a set of all values where the key satisfies {@code testFunction}
     */
    public static <K, V> Set<V> valuesWithKeyMatching(Map<K, V> items,
                                                      Predicate<? super K> testFunction) {
        return valuesWithEntryMatching(items, e -> testFunction.test(e.getKey()));
    }

    public static <K, V> AbstractMap.SimpleImmutableEntry<K, V> createEntry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    public static <K, V> Map<K, V> mergeMaps(Map<K, V> m1,
                                             Map<K, V> m2,
                                             BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Map<K, V> result = new HashMap<>(m1);
        m2.forEach((k, v) -> result.merge(k, v, remappingFunction));
        return result;
    }

    /**
     * Computes the difference between two maps.
     * <p>
     * Not intended to be used with null keys or values.
     *
     * @param left  the map to treat as the "left" map for purposes of comparison
     * @param right the map to treat as the "right" map for purposes of comparison
     * @return the difference between the two maps
     */
    public static <K, V> Map<K, V> difference(Map<K, V> left, Map<K, V> right) {
        Map<K, V> result = new HashMap<>();
        left.entrySet().stream()
                .filter(entry -> !(
                        right.containsKey(entry.getKey()) &&
                                Objects.equals(entry.getValue(), right.get(entry.getKey()))
                ))
                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        return result;
    }
}
