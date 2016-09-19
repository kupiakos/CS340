package shared.utils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapUtils {
    // TODO: Javadocs
    public static <K, V> Set<K> keysWithEntryMatching(Map<K, V> items,
                                                      Predicate<Map.Entry<K, V>> testFunction) {
        // value or any contained value in items may be null
        return items.entrySet().stream()
                .filter(testFunction)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static <K, V> Set<K> keysWithValueMatching(Map<K, V> items,
                                                      Predicate<V> testFunction) {
        // value or any contained value in items may be null
        return keysWithEntryMatching(items, e -> testFunction.test(e.getValue()));
    }

    public static <K, V> Set<K> keysWithValue(Map<K, V> items, V value) {
        // value or any contained value in items may be null
        return keysWithEntryMatching(items, e -> Objects.equals(e.getValue(), value));
    }

    public static <K, V> Set<V> valuesMatching(Map<K, V> items,
                                               Predicate<? super V> testFunction) {
        // value or any contained value in items may be null
        return items.values()
                .stream()
                .filter(testFunction)
                .collect(Collectors.toSet());
    }
}
