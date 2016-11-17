package shared.definitions;

import com.google.gson.annotations.SerializedName;

/**
 * The types of development cards.
 */
public enum DevCardType {
    @SerializedName("Soldier")
    SOLDIER,

    @SerializedName("Year_of_Plenty")
    YEAR_OF_PLENTY,

    @SerializedName("Monopoly")
    MONOPOLY,

    @SerializedName("Road_Building")
    ROAD_BUILD,

    @SerializedName("Monument")
    MONUMENT
}

