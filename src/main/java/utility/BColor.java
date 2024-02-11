package utility;

/**
 * Board Colors.
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
     * Grey color, the default one.
     */
    GREY("#f1f1f1");

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
