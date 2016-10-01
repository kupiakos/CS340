package shared.models.game;

import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class CardSet<CardType extends Enum<?>> {
    /**
     * Returns a copy of {@code set} that swaps the signs of each of its resources
     *
     * @param set the set to form a negative copy of
     * @return a negative copy of {@code set}
     */
    public static <type extends Enum<?>> CardSet<type> toNegative(@NotNull CardSet<type> set) {
        Objects.requireNonNull(set);
        CardSet<type> result = set.copy();
        result.toNegative();
        return result;
    }

    /**
     * Combine two resource sets, summing their contents.
     *
     * @param set1 the first set, not null
     * @param set2 the second set, not null
     * @return a new {@link CardSet<type>} containing the sum of their contents.
     * @pre {@code set1} and {@code set2} are valid resource sets
     * @post The return will be valid. {@code set1} and {@code set2} are unmodified.
     */
    public static <type extends Enum<?>> CardSet<type> combined(@NotNull CardSet<type> set1, @NotNull CardSet<type> set2) {
        CardSet<type> result = set1.copy();
        result.combine(set2);
        return result;
    }

    /**
     * Combines two resource sets, subtracting the contents of {@code set1} from {@code set2}
     *
     * @param set1 the set (copied) to be subtracted from, not null
     * @param set2 the set to subtract with, not null
     * @return a new {@link CardSet<type>} containing the difference of their contents.
     * @pre {@code set1} and {@code set2} are valid resource sets
     * @post The return will be valid. {@code set1} and {@code set2} are unmodified.
     */
    public static <type extends Enum<?>> CardSet<type> subtracted(@NotNull CardSet<type> set1, @NotNull CardSet<type> set2) {
        CardSet<type> result = set1.copy();
        result.subtract(set2);
        return result;
    }

    public abstract CardSet<CardType> copy();

    public abstract int getOfType(CardType type);

    public abstract void setOfType(@NotNull CardType type, int value);

    protected abstract Stream<CardType> getTypes();

    protected IntStream getValues() {
        return getTypes().mapToInt(this::getOfType);
    }

    /**
     * Determines whether all of the resources in this are less than or equal to {@code other}.
     * <p>
     * If true, then .
     *
     * @param other the set to determine if larger
     * @return true if all of the resources in this are less than ore equal to in {@code other}
     */
    public boolean isSubset(@NotNull CardSet<CardType> other) {
        return getTypes().allMatch(t -> getOfType(t) <= other.getOfType(t));
    }

    /**
     * Determines whether all of the resources in this are more than or equal to {@code other}.
     * <p>
     * Effectively determines whether the given resources are required.
     *
     * @param other the set to determine if larger
     * @return true if all of the resources in this are less than ore equal to in {@code other}
     */
    public boolean isSuperset(@NotNull CardSet<CardType> other) {
        return getTypes().allMatch(t -> getOfType(t) >= other.getOfType(t));
    }

    /**
     * Whether all of the resources are more than or equal to 0.
     *
     * @return true if all resources are more than or equal to 0; false otherwise.
     */
    public boolean isPositive() {
        return getValues().allMatch(v -> v >= 0);
    }

    /**
     * Whether all of the resources are less than or equal to 0.
     *
     * @return true if at least one resource is less than 0; false otherwise
     */
    public boolean isNegative() {
        return getValues().allMatch(v -> v <= 0);
    }

    /**
     * Whether all of the resources are equal to 0.
     *
     * @return true if all resources are equal to 0; false otherwise
     */
    public boolean isEmpty() {
        return getValues().allMatch(v -> v == 0);
    }

    /**
     * Whether any of the resources are more than 0.
     *
     * @return true if all resources are more than or equal to 0; false otherwise.
     */
    public boolean hasPositive() {
        return getValues().anyMatch(v -> v > 0);
    }

    /**
     * Whether any of the resources are less than 0.
     *
     * @return true if at least one resource is less than 0; false otherwise
     */
    public boolean hasNegative() {
        return getValues().anyMatch(v -> v < 0);
    }

    /**
     * Combines this with another resource list, summing their contents.
     *
     * @param other the other set to combine with, not null
     * @pre {@code other} is a valid resource set
     * @post This will be summed with the resources in {@code other},
     * but {@code other} will not be modified.
     */
    public void combine(@NotNull CardSet<CardType> other) {
        getTypes().forEach(type ->
                setOfType(type, getOfType(type) + other.getOfType(type))
        );
    }

    /**
     * Combines this with another resource list, subtracting with {@code other}'s contents.
     *
     * @param other the other set to subtract from, not null
     * @pre {@code other} is a valid resource set
     * @post This will be summed with the resources in {@code other},
     * but {@code other} will not be modified.
     */
    public void subtract(@NotNull CardSet<CardType> other) {
        getTypes().forEach(type ->
                setOfType(type, getOfType(type) - other.getOfType(type))
        );
    }

    /**
     * Swap the signs of each of this set's resources
     *
     * @return a negative copy of {@code set}
     */
    public void toNegative() {
        getTypes().forEach(type ->
                setOfType(type, -getOfType(type))
        );
    }

    public int getTotal() {
        return getTypes().mapToInt(this::getOfType).sum();
    }

    /**
     * Pick a single random card from this resource set.
     * Does not modify the set.
     *
     * @return the ResourceType chosen, or null if the set is empty
     */
    @Nullable
    public CardType getRandom() {
        // Inefficient, but should work.
        if (isEmpty()) {
            return null;
        }

        int n = new Random().nextInt(getTotal());
        return getTypes()
                .flatMap(t -> IntStream.range(0, getOfType(t)).mapToObj(v -> t))
                .skip(n)
                .findFirst().orElseGet(null);
    }
}
