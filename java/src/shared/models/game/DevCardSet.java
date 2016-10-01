package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import shared.definitions.DevCardType;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.stream.Stream;

@Generated("net.kupiakos")
public class DevCardSet extends CardSet<DevCardType> {

    @SerializedName("yearOfPlenty")
    @Expose
    private int yearOfPlenty;

    @SerializedName("roadBuilding")
    @Expose
    private int roadBuilding;

    @SerializedName("monument")
    @Expose
    private int monument;

    @SerializedName("soldier")
    @Expose
    private int soldier;

    @SerializedName("monopoly")
    @Expose
    private int monopoly;


    /**
     * No args constructor for use in serialization
     */
    public DevCardSet() {
    }

    /**
     * @param yearOfPlenty The Year of Plenty Cards
     * @param roadBuilding The Road Building Cards
     * @param monument     The Monument (Victory Point) Cards
     * @param soldier      The Knight/Soldier Cards
     * @param monopoly     The Monopoly Cards
     */
    public DevCardSet(int yearOfPlenty, int roadBuilding, int monument, int soldier, int monopoly) {
        this.yearOfPlenty = yearOfPlenty;
        this.roadBuilding = roadBuilding;
        this.monument = monument;
        this.soldier = soldier;
        this.monopoly = monopoly;
    }

    // CUSTOM CODE

    @Override
    public CardSet<DevCardType> copy() {
        return new DevCardSet(yearOfPlenty, roadBuilding, monument, soldier, monopoly);
    }

    /**
     * Get the number of cards in this DevCardList of a specific type
     *
     * @param type the type to check against
     * @return the number of cards, less than 0
     */
    public int getOfType(DevCardType type) {
        switch (type) {
            case MONOPOLY:
                return monopoly;
            case ROAD_BUILD:
                return roadBuilding;
            case MONUMENT:
                return monument;
            case SOLDIER:
                return soldier;
            case YEAR_OF_PLENTY:
                return yearOfPlenty;
            default:
                assert false;
                return 0;
        }
    }

    @Override
    public void setOfType(@NotNull DevCardType type, int value) {
        switch (type) {
            case SOLDIER:
                setSoldier(value);
                break;
            case YEAR_OF_PLENTY:
                setYearOfPlenty(value);
                break;
            case MONOPOLY:
                setMonopoly(value);
                break;
            case ROAD_BUILD:
                setRoadBuilding(value);
                break;
            case MONUMENT:
                setMonument(monument);
                break;
            default:
                assert false;
        }
    }

    @Override
    protected Stream<DevCardType> getTypes() {
        return Arrays.stream(DevCardType.values());
    }

    // END CUSTOM CODE

    /**
     * @return The Year of Plenty Cards
     */
    public int getYearOfPlenty() {
        return yearOfPlenty;
    }

    /**
     * @param yearOfPlenty The Year of Plenty Cards
     */
    public void setYearOfPlenty(int yearOfPlenty) {
        if (yearOfPlenty < 0 || yearOfPlenty > 2) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 2");
        }
        this.yearOfPlenty = yearOfPlenty;
    }

    public DevCardSet withYearOfPlenty(int yearOfPlenty) {
        setYearOfPlenty(yearOfPlenty);
        return this;
    }

    /**
     * @return The Road Building Cards
     */
    public int getRoadBuilding() {
        return roadBuilding;
    }

    /**
     * @param roadBuilding The Road Building Cards
     */
    public void setRoadBuilding(int roadBuilding) {
        if (roadBuilding < 0 || roadBuilding > 2) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 2");
        }
        this.roadBuilding = roadBuilding;
    }

    public DevCardSet withRoadBuilding(int roadBuilding) {
        setRoadBuilding(roadBuilding);
        return this;
    }

    /**
     * @return The Monument (Victory Point) Cards
     */
    public int getMonument() {
        return monument;
    }

    /**
     * @param monument The Monument (Victory Point) Cards
     */
    public void setMonument(int monument) {
        if (monument < 0 || monument > 5) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 5");
        }
        this.monument = monument;
    }

    public DevCardSet withMonument(int monument) {
        setMonument(monument);
        return this;
    }

    /**
     * @return The Knight/Soldier Cards
     */
    public int getSoldier() {
        return soldier;
    }

    /**
     * @param soldier The Knight/Soldier Cards
     */
    public void setSoldier(int soldier) {
        if (soldier < 0 || soldier > 14) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 14");
        }
        this.soldier = soldier;
    }

    public DevCardSet withSoldier(int soldier) {
        setSoldier(soldier);
        return this;
    }

    /**
     * @return The Monopoly Cards
     */
    public int getMonopoly() {
        return monopoly;
    }

    /**
     * @param monopoly The Monopoly Cards
     */
    public void setMonopoly(int monopoly) {
        if (monopoly < 0 || monopoly > 2) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 2");
        }
        this.monopoly = monopoly;
    }

    public DevCardSet withMonopoly(int monopoly) {
        setMonopoly(monopoly);
        return this;
    }

    @Override
    public String toString() {
        return "DevCardList [" +
                "yearOfPlenty=" + yearOfPlenty +
                ", roadBuilding=" + roadBuilding +
                ", monument=" + monument +
                ", soldier=" + soldier +
                ", monopoly=" + monopoly +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof DevCardSet) {
            return equals((DevCardSet) other);
        }
        return false;
    }

    public boolean equals(DevCardSet other) {
        return (
                yearOfPlenty == other.yearOfPlenty &&
                        roadBuilding == other.roadBuilding &&
                        monument == other.monument &&
                        soldier == other.soldier &&
                        monopoly == other.monopoly
        );
    }
}
