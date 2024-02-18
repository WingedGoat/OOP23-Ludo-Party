package view.utils;

import java.nio.file.Path;

/**
 * Utility enum containing the path of each resource used.
 */
public enum ResourcePath {

    /**
     * the title font.
     */
    TITLE_FONT(Path.of(getResourcesFoder() + "fonts" + getFileSep() + "MajorMonoDisplay.ttf")),
    /**
     * the arrow icon.
     */
    ARROW_ICON(Path.of(getImagesFoder() + "arrow-right.png")),
    /**
     * the background image.
     */
    BACKGROUND_IMG(Path.of(getImagesFoder() + "space-bg.png")),
    /**
     * the dice image with face one.
     */
    DICE_IMG_FACE_ONE(Path.of(getDicesFoder() + "dice-1.png")),
    /**
     * the dice image with face two.
     */
    DICE_IMG_FACE_TWO(Path.of(getDicesFoder() + "dice-2.png")),
    /**
     * the dice image with face three.
     */
    DICE_IMG_FACE_THREE(Path.of(getDicesFoder() + "dice-3.png")),
    /**
     * the dice image with face four.
     */
    DICE_IMG_FACE_FOUR(Path.of(getDicesFoder() + "dice-4.png")),
    /**
     * the dice image with face five.
     */
    DICE_IMG_FACE_FIVE(Path.of(getDicesFoder() + "dice-5.png")),
    /**
     * the dice image with face six.
     */
    DICE_IMG_FACE_SIX(Path.of(getDicesFoder() + "dice-6.png"));


    private final Path path;

    ResourcePath(final Path path) {
        this.path = path;
    }

    /**
     * Returns the resource path.
     * @return the resource path
     */
    public String getPath() {
        return path.toString();
    }

    private static String getResourcesFoder() {
        return ViewUtility.RESOURCES_FOLDER;
    }

    private static String getImagesFoder() {
        return ViewUtility.IMAGES_FOLDER;
    }

    private static String getDicesFoder() {
        return ViewUtility.DICES_FOLDER;
    }

    private static String getFileSep() {
        return ViewUtility.FILE_SEPARATOR;
    }

}
