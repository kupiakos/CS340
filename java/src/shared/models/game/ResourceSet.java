package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.ResourceType;

import javax.annotation.Generated;
import java.util.Objects;
import java.util.stream.Stream;

@Generated("net.kupiakos")
public class ResourceSet extends CardSet<ResourceType> {

    @SerializedName("ore")
    @Expose
    private int ore;

    @SerializedName("brick")
    @Expose
    private int brick;

    @SerializedName("sheep")
    @Expose
    private int sheep;

    @SerializedName("wood")
    @Expose
    private int wood;

    @SerializedName("wheat")
    @Expose
    private int wheat;


    // CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public ResourceSet() {
    }

    /**
     * @param ore   The ore
     * @param brick The brick
     * @param sheep The sheep
     * @param wood  The wood
     * @param wheat The wheat
     */
    public ResourceSet(int ore, int brick, int sheep, int wood, int wheat) {
        this.ore = ore;
        this.brick = brick;
        this.sheep = sheep;
        this.wood = wood;
        this.wheat = wheat;
    }

    public ResourceSet(ResourceSet other) {
        for (ResourceType type : ResourceType.values()) {
            setOfType(type, getOfType(type) + other.getOfType(type));
        }
    }

    public ResourceSet(ResourceType type, int value) {
        setOfType(type, value);
    }

    /**
     * Returns a copy of {@code set} that swaps the signs of each of its resources
     *
     * @param set the set to form a negative copy of
     * @return a negative copy of {@code set}
     */
    public static ResourceSet toNegative(@NotNull ResourceSet set) {
        Objects.requireNonNull(set);
        ResourceSet result = new ResourceSet();
        result.toNegative();
        return result;
    }

    /**
     * Combine two resource sets, summing their contents.
     *
     * @param set1 the first set, not null
     * @param set2 the second set, not null
     * @return a new {@link ResourceSet} containing the sum of their contents.
     * @pre {@code set1} and {@code set2} are valid resource sets
     * @post The return will be valid. {@code set1} and {@code set2} are unmodified.
     */
    public static ResourceSet combined(@NotNull ResourceSet set1, @NotNull ResourceSet set2) {
        ResourceSet result = set1.copy();
        result.combine(set2);
        return result;
    }

    /**
     * Combines two resource sets, subtracting the contents of {@code set1} from {@code set2}
     *
     * @param set1 the set (copied) to be subtracted from, not null
     * @param set2 the set to subtract with, not null
     * @return a new {@link ResourceSet} containing the difference of their contents.
     * @pre {@code set1} and {@code set2} are valid resource sets
     * @post The return will be valid. {@code set1} and {@code set2} are unmodified.
     */
    public static ResourceSet subtracted(@NotNull ResourceSet set1, @NotNull ResourceSet set2) {
        ResourceSet result = set1.copy();
        result.subtract(set2);
        return result;
    }

    /**
     * Returns a copy of this object.
     *
     * @return a copy of this object
     */
    @Override
    public ResourceSet copy() {
        return new ResourceSet(ore, brick, sheep, wood, wheat);
    }

    @Override
    protected Stream<ResourceType> getTypes() {
        return ResourceType.valuesStream();
    }

    /**
     * Get the amount of a type of resource represented by this list.
     *
     * @param type the type of resource to get
     * @return the amount of that resource represented
     */
    public int getOfType(@NotNull ResourceType type) {
        switch (type) {
            case WOOD:
                return getWood();
            case BRICK:
                return getBrick();
            case SHEEP:
                return getSheep();
            case WHEAT:
                return getWheat();
            case ORE:
                return getOre();
            default:
                assert false;
                return 0;
        }
    }

    /**
     * Set the amount of a type of resource represented by this list.
     *
     * @param type  the type of resource to set, not null
     * @param value the amount that resource will represent, not null
     * @pre neither attributes are null
     */
    public void setOfType(@NotNull ResourceType type, int value) {
        switch (type) {
            case WOOD:
                setWood(value);
                break;
            case BRICK:
                setBrick(value);
                break;
            case SHEEP:
                setSheep(value);
                break;
            case WHEAT:
                setWheat(value);
                break;
            case ORE:
                setOre(value);
                break;
            default:
                assert false;
                break;
        }
    }

    /**
     * @return The ore
     */
    public int getOre() {
        return ore;
    }

    /**
     * @param ore The ore
     */
    public void setOre(int ore) {
        this.ore = ore;
    }

    public ResourceSet withOre(int ore) {
        setOre(ore);
        return this;
    }

    /**
     * @return The brick
     */
    public int getBrick() {
        return brick;
    }

    /**
     * @param brick The brick
     */
    public void setBrick(int brick) {
        this.brick = brick;
    }

    public ResourceSet withBrick(int brick) {
        setBrick(brick);
        return this;
    }

    /**
     * @return The sheep
     */
    public int getSheep() {
        return sheep;
    }

    /**
     * @param sheep The sheep
     */
    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    public ResourceSet withSheep(int sheep) {
        setSheep(sheep);
        return this;
    }

    /**
     * @return The wood
     */
    public int getWood() {
        return wood;
    }

    /**
     * @param wood The wood
     */
    public void setWood(int wood) {
        this.wood = wood;
    }

    public ResourceSet withWood(int wood) {
        setWood(wood);
        return this;
    }

    /**
     * @return The wheat
     */
    public int getWheat() {
        return wheat;
    }

    /**
     * @param wheat The wheat
     */
    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public ResourceSet withWheat(int wheat) {
        setWheat(wheat);
        return this;
    }

    @Override
    public String toString() {
        return "ResourceList [" +
                "ore=" + ore +
                ", brick=" + brick +
                ", sheep=" + sheep +
                ", wood=" + wood +
                ", wheat=" + wheat +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ResourceSet) {
            return equals((ResourceSet) other);
        }
        return false;
    }

    public boolean equals(ResourceSet other) {
        return (
                ore == other.ore &&
                        brick == other.brick &&
                        sheep == other.sheep &&
                        wood == other.wood &&
                        wheat == other.wheat
        );
    }
}
