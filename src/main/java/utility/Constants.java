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
     * Board bottom height.
     */
    public static final int BOARD_BOTTOM_HEIGHT = 50;
    /**
     * Inset offset.
     */
    public static final int INSET_OS = 10;
    /**
     * Four players game.
     */
    public static final int FOUR_PLAYERS_GAME = 4;
    /**
     * Number of cells of the board.
     */
    public static final int BOARD_CELLS = 15;

    private Constants() { }

}
