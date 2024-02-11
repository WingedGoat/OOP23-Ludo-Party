package utility;

/**
 * Board Colors.
 */
public enum BColor {
    /**
     * Green color.
     */
    GREEN("#10793e"),
    /**
     * Red color.
     */
    RED("#e42e25"),
    /**
     * Blue color.
     */
    BLUE("#2575ba"),
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
     * @param hex hex color
     */
    BColor(final String hex) {
        // This constructor is intentionally empty
        this.hexColor = hex;
    }

    /**
     * Gets the color.
     * @return the color
     */
    public String get() {
        return hexColor;
    }

}
