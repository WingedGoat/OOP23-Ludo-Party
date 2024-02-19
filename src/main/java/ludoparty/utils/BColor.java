package ludoparty.utils;

import javafx.scene.paint.Color;

/**
 * Board Colors.
 * @see BLUE
 * @see RED
 * @see GREEN
 * @see YELLOW
 * @see GREY
 * @see DARK_BLUE
 * @see DARK_RED
 * @see DARK_GREEN
 * @see DARK_YELLOW
 * @see DARK_GREY
 * @see LIGHT_BLACK
 */
public enum BColor {
    /**
     * Blue color.
     */
    BLUE("#2575ba"),
    /**
     * Red color.
     */
    RED("#e42e25"),
    /**
     * Green color.
     */
    GREEN("#10793e"),
    /**
     * Yellow color.
     */
    YELLOW("#fec00f"),
    /**
     * Dark blue color.
     */
    DARK_BLUE("#2559ba"),
    /**
     * Dark red color.
     */
    DARK_RED("#c42e14"),
    /**
     * Dark green color.
     */
    DARK_GREEN("#0d6634"),
    /**
     * Dark yellow color.
     */
    DARK_YELLOW("#feaa0f"),
    /**
     * Grey color, the default one.
     */
    GREY("#f1f1f1"),
    /**
     * Dark grey.
     */
    DARK_GREY("#363230"),
    /**
     * Shop grey.
     */
    SHOP_GREY("#bcbec4"),
    /**
     * Light black.
     */
    LIGHT_BLACK("#282a35"),
    /**
     * Grey circle.
     */
    GREY_CIRCLE("#555555");

    private String hexColor;

    /**
     * Constructor.
     * 
     * @param hex hex color
     */
    BColor(final String hex) {
        this.hexColor = hex;
    }

    /**
     * Gets the hexadecimal color.
     * @return the hexadecimal color
     */
    public String getHexColor() {
        return hexColor;
    }

    /**
     * Gets the color.
     * 
     * @return the color
     */
    public Color get() {
        return Color.valueOf(hexColor);
    }

}
