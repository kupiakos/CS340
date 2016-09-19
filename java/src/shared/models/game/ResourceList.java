package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.NotNull;
import shared.definitions.ResourceType;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.Objects;

@Generated("net.kupiakos")
public class ResourceList {

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
    public ResourceList() {
    }

    /**
     * @param ore   The ore
     * @param brick The brick
     * @param sheep The sheep
     * @param wood  The wood
     * @param wheat The wheat
     */
    public ResourceList(int ore, int brick, int sheep, int wood, int wheat) {
        this.ore = ore;
        this.brick = brick;
        this.sheep = sheep;
        this.wood = wood;
        this.wheat = wheat;
    }

    /**
     * Returns a copy of {@code list} that swaps the signs of each of its resources
     *
     * @param list the list to form a negative copy of
     * @return a negative copy of {@code list}
     */
    public static ResourceList toNegative(@NotNull ResourceList list) {
        Objects.requireNonNull(list);
        ResourceList result = new ResourceList();
        for (ResourceType type : ResourceType.values()) {
            result.setOfType(type, -list.getOfType(type));
        }
        return result;
    }

    /**
     * Combine two resource lists, summing their contents.
     *
     * @param list1 the first list, not null
     * @param list2 the second list, not null
     * @return a new {@link ResourceList} containing the sum of their contents.
     * @pre {@code list1} and {@code list2} are valid resource lists
     * @post The return will be valid. {@code list1} and {@code list2} are unmodified.
     */
    public static ResourceList combine(@NotNull ResourceList list1, @NotNull ResourceList list2) {
        Objects.requireNonNull(list1);
        Objects.requireNonNull(list2);
        // look into using cloning for this
        ResourceList result = new ResourceList();
        for (ResourceType type : ResourceType.values()) {
            result.setOfType(type, list1.getOfType(type) + list2.getOfType(type));
        }
        return result;
    }

    // TODO: isSubset, subtract

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
            case BRICK:
                setBrick(value);
            case SHEEP:
                setSheep(value);
            case WHEAT:
                setWheat(value);
            case ORE:
                setOre(value);
            default:
                break;
        }
    }


    // END CUSTOM CODE

    /**
     * Whether any of the resources are less than 0.
     *
     * @return true if at least one resource is less than 0; false otherwise
     */
    public boolean isNegative() {
        return Arrays.stream(ResourceType.values())
                .anyMatch(t -> getOfType(t) < 0);
    }

    /**
     * Combines this with another resource list, summing their contents.
     *
     * @param other the other list to combine with, not null
     * @pre {@code other} is a valid resource list
     * @post This will be summed with the resources in {@code other},
     * but {@code other} will not be modified.
     */
    public void combine(ResourceList other) {
        if (other == null) {
            return;
        }
        for (ResourceType type : ResourceType.values()) {
            setOfType(type, getOfType(type) + other.getOfType(type));
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

    public ResourceList withOre(int ore) {
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

    public ResourceList withBrick(int brick) {
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

    public ResourceList withSheep(int sheep) {
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

    public ResourceList withWood(int wood) {
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

    public ResourceList withWheat(int wheat) {
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
        if (other instanceof ResourceList) {
            return equals((ResourceList) other);
        }
        return false;
    }

    public boolean equals(ResourceList other) {
        return (
                ore == other.ore &&
                        brick == other.brick &&
                        sheep == other.sheep &&
                        wood == other.wood &&
                        wheat == other.wheat
        );
    }
}
