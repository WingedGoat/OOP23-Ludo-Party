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
    ARROW_ICON(Path.of(getResourcesFoder() + "images" + getFileSep() + "arrow-right.png")),
    /**
     * the background image.
     */
    BACKGROUND_IMG(Path.of(getResourcesFoder() + "images" + getFileSep() + "space-bg.png")),
    /**
     * the dice image folder.
     */
    DICE_IMG_FOLDER(Path.of(getResourcesFoder() + "images" + getFileSep() + "dices")),
    /**
     * the dice image with face one.
     */
    DICE_IMG_FACE_ONE(Path.of(DICE_IMG_FOLDER.getPath() + getFileSep() + "dice-1.png"));


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

    private static String getFileSep() {
        return ViewUtility.FILE_SEPARATOR;
    }

}
