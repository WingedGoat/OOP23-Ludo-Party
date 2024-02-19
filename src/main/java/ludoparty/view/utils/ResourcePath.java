package ludoparty.view.utils;

/**
 * Utility enum containing the path of each resource used.
 */
public enum ResourcePath {

    /**
     * the title font.
     */
    TITLE_FONT(getRootFolder() + "MajorMonoDisplay.ttf"),
    /**
     * the arrow icon.
     */
    ARROW_ICON(getRootFolder() + "arrow-right.png"),
    /**
     * the background image.
     */
    BACKGROUND_IMG(getRootFolder() + "space-bg.png"),
    /**
     * the dice image with face one.
     */
    DICE_IMG_FACE_ONE(getRootFolder() + "dice-1.png"),
    /**
     * the dice image with face two.
     */
    DICE_IMG_FACE_TWO(getRootFolder() + "dice-2.png"),
    /**
     * the dice image with face three.
     */
    DICE_IMG_FACE_THREE(getRootFolder() + "dice-3.png"),
    /**
     * the dice image with face four.
     */
    DICE_IMG_FACE_FOUR(getRootFolder() + "dice-4.png"),
    /**
     * the dice image with face five.
     */
    DICE_IMG_FACE_FIVE(getRootFolder() + "dice-5.png"),
    /**
     * the dice image with face six.
     */
    DICE_IMG_FACE_SIX(getRootFolder() + "dice-6.png");


    private final String path;

    ResourcePath(final String path) {
        this.path = path;
    }

    /**
     * Returns the resource path.
     * @return the resource path
     */
    public String getPath() {
        return path;
    }

    private static String getRootFolder() {
        return ViewUtility.RESOURCES_ROOT_PATH;
    }
    /*
    private static String getFontsFolder() {
        return ViewUtility.FONTS_ROOT_PATH;
    }

    private static String getImagesFoder() {
        return ViewUtility.IMAGES_FOLDER;
    }

    private static String getDicesFoder() {
        return ViewUtility.DICES_FOLDER;
    }
     */

}
