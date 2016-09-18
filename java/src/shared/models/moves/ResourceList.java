package shared.models.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

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
    // END CUSTOM CODE

    /**
     * No args constructor for use in serialization
     */
    public ResourceList() {
    }

    /**
      * @param ore The ore
      * @param brick The brick
      * @param sheep The sheep
      * @param wood The wood
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
     * @return The ore
     */
    public int getOre() { return ore; }

    /**
     * @param ore The ore
     */
    public void setOre(int ore) { this.ore = ore; }

    public ResourceList withOre(int ore) {
        setOre(ore);
        return this;
    }
    /**
     * @return The brick
     */
    public int getBrick() { return brick; }

    /**
     * @param brick The brick
     */
    public void setBrick(int brick) { this.brick = brick; }

    public ResourceList withBrick(int brick) {
        setBrick(brick);
        return this;
    }
    /**
     * @return The sheep
     */
    public int getSheep() { return sheep; }

    /**
     * @param sheep The sheep
     */
    public void setSheep(int sheep) { this.sheep = sheep; }

    public ResourceList withSheep(int sheep) {
        setSheep(sheep);
        return this;
    }
    /**
     * @return The wood
     */
    public int getWood() { return wood; }

    /**
     * @param wood The wood
     */
    public void setWood(int wood) { this.wood = wood; }

    public ResourceList withWood(int wood) {
        setWood(wood);
        return this;
    }
    /**
     * @return The wheat
     */
    public int getWheat() { return wheat; }

    /**
     * @param wheat The wheat
     */
    public void setWheat(int wheat) { this.wheat = wheat; }

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
            return equals((ResourceList)other);
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
