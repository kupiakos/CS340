package shared.definitions;

import com.google.gson.annotations.SerializedName;

import java.awt.*;

/**
 * The color of a {@link shared.models.game.Player Player} displayed to the user.
 */
public enum CatanColor {
    @SerializedName("red")
    RED(new Color(227, 66, 52)),

    @SerializedName("orange")
    ORANGE(new Color(255, 165, 0)),

    @SerializedName("yellow")
    YELLOW(new Color(253, 224, 105)),

    @SerializedName("blue")
    BLUE(new Color(111, 183, 246)),

    @SerializedName("green")
    GREEN(new Color(109, 192, 102)),

    @SerializedName("purple")
    PURPLE(new Color(157, 140, 212)),

    @SerializedName("puce")
    PUCE(new Color(204, 136, 153)),

    @SerializedName("white")
    WHITE(new Color(223, 223, 223)),

    @SerializedName("brown")
    BROWN(new Color(161, 143, 112));

    private Color color;

    CatanColor(Color c) {
        color = c;
    }

    public Color getJavaColor() {
        return color;
    }
}

