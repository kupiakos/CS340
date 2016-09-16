package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Resources {

    @SerializedName("brick")
    @Expose
    private int brick;
    @SerializedName("ore")
    @Expose
    private int ore;
    @SerializedName("sheep")
    @Expose
    private int sheep;
    @SerializedName("wheat")
    @Expose
    private int wheat;
    @SerializedName("wood")
    @Expose
    private int wood;

    /**
     * No args constructor for use in serialization
     */
    public Resources() {
    }

    /**
     * @param wheat
     * @param wood
     * @param brick
     * @param sheep
     * @param ore
     */
    public Resources(int brick, int ore, int sheep, int wheat, int wood) {
        this.brick = brick;
        this.ore = ore;
        this.sheep = sheep;
        this.wheat = wheat;
        this.wood = wood;
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

    public Resources withBrick(int brick) {
        this.brick = brick;
        return this;
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

    public Resources withOre(int ore) {
        this.ore = ore;
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

    public Resources withSheep(int sheep) {
        this.sheep = sheep;
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

    public Resources withWheat(int wheat) {
        this.wheat = wheat;
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

    public Resources withWood(int wood) {
        this.wood = wood;
        return this;
    }

}
