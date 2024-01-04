package utility;

import java.nio.file.Path;

/**
 * Class containing useful constants.
 */
public final class Constants {

    /**
     * Root path.
     */
    public static final Path ROOT_PATH = Path.of("." + System.getProperty("file.separator"));
    /**
     * Home window width.
     */
    public static final int HOME_WINDOW_WIDTH = 450;
    /**
     * Home window height.
     */
    public static final int HOME_WINDOW_HEIGHT = 600;
    /**
     * Inset offset.
     */
    public static final int INSET_OS = 10;

    private Constants() { }

}
