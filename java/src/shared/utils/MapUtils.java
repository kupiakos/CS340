package shared.utils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapUtils {
    public static <K, V> Set<K> getKeysWithEntryMatching(Map<K, V> items,
                                                         Predicate<? super Map.Entry<K, V>> testFunction) {
        // value or any contained value in items may be null
        return items.entrySet().stream()
                .filter(testFunction)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static <K, V> Set<K> getKeysWithValueMatching(Map<K, V> items,
                                                         Predicate<? super V> testFunction) {
        // value or any contained value in items may be null
        return getKeysWithEntryMatching(items, e -> testFunction.test(e.getValue()));
    }

    public static <K, V> Set<K> getKeysWithValue(Map<K, V> items, V value) {
        // value or any contained value in items may be null
        return getKeysWithEntryMatching(items, e -> Objects.equals(e.getValue(), value));
    }
}
