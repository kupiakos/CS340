package shared.definitions;

import com.google.gson.annotations.SerializedName;

import java.awt.*;

/**
 * The color of a {@link shared.models.game.Player Player} displayed to the user.
 */
public enum CatanColor
{
    @SerializedName("Red")
	RED(new Color(227, 66, 52)),

    @SerializedName("Orange")
	ORANGE(new Color(255, 165, 0)),

    @SerializedName("Yellow")
    YELLOW(new Color(253, 224, 105)),

    @SerializedName("Blue")
    BLUE(new Color(111, 183, 246)),

    @SerializedName("Green")
    GREEN(new Color(109, 192, 102)),

    @SerializedName("Purple")
    PURPLE(new Color(157, 140, 212)),

    @SerializedName("Puce")
    PUCE(new Color(204, 136, 153)),

    @SerializedName("White")
    WHITE(new Color(223, 223, 223)),

    @SerializedName("Brown")
    BROWN(new Color(161, 143, 112));
	
	private Color color;

    CatanColor(Color c) {
        color = c;
    }

	public Color getJavaColor()
	{
		return color;
	}
}

