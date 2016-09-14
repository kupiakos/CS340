package shared.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ResourceList {

    @SerializedName("brick")
    @Expose
    private String brick;
    @SerializedName("ore")
    @Expose
    private String ore;
    @SerializedName("sheep")
    @Expose
    private String sheep;
    @SerializedName("wheat")
    @Expose
    private String wheat;
    @SerializedName("wood")
    @Expose
    private String wood;

    /**
     * No args constructor for use in serialization
     */
    public ResourceList() {
    }

    /**
     * @param wheat
     * @param wood
     * @param brick
     * @param sheep
     * @param ore
     */
    public ResourceList(String brick, String ore, String sheep, String wheat, String wood) {
        this.brick = brick;
        this.ore = ore;
        this.sheep = sheep;
        this.wheat = wheat;
        this.wood = wood;
    }

    /**
     * @return The brick
     */
    public String getBrick() {
        return brick;
    }

    /**
     * @param brick The brick
     */
    public void setBrick(String brick) {
        this.brick = brick;
    }

    public ResourceList withBrick(String brick) {
        this.brick = brick;
        return this;
    }

    /**
     * @return The ore
     */
    public String getOre() {
        return ore;
    }

    /**
     * @param ore The ore
     */
    public void setOre(String ore) {
        this.ore = ore;
    }

    public ResourceList withOre(String ore) {
        this.ore = ore;
        return this;
    }

    /**
     * @return The sheep
     */
    public String getSheep() {
        return sheep;
    }

    /**
     * @param sheep The sheep
     */
    public void setSheep(String sheep) {
        this.sheep = sheep;
    }

    public ResourceList withSheep(String sheep) {
        this.sheep = sheep;
        return this;
    }

    /**
     * @return The wheat
     */
    public String getWheat() {
        return wheat;
    }

    /**
     * @param wheat The wheat
     */
    public void setWheat(String wheat) {
        this.wheat = wheat;
    }

    public ResourceList withWheat(String wheat) {
        this.wheat = wheat;
        return this;
    }

    /**
     * @return The wood
     */
    public String getWood() {
        return wood;
    }

    /**
     * @param wood The wood
     */
    public void setWood(String wood) {
        this.wood = wood;
    }

    public ResourceList withWood(String wood) {
        this.wood = wood;
        return this;
    }

}
