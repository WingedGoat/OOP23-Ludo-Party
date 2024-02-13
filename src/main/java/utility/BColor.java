package utility;

/**
 * Board Colors.
 * @see BLUE
 * @see RED
 * @see GREEN
 * @see YELLOW
 * @see DARK_BLUE
 * @see DARK_RED
 * @see DARK_GREEN
 * @see DARK_YELLOW
 * @see GREY
 * @see DARK_GREY
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
    DARK_GREY("#363230");

    private String hexColor;

    /**
     * Constructor.
     * 
     * @param hex hex color
     */
    BColor(final String hex) {
        // This constructor is intentionally empty
        this.hexColor = hex;
    }

    /**
     * Gets the color.
     * 
     * @return the color
     */
    public String get() {
        return hexColor;
    }
}
