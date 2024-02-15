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
    public static final int BOARD_BOTTOM_HEIGHT = 200; 
    /**
     * Button height.
     */
    public static final int BUTTON_HEIGHT = 95;
    /**
     * button widht.
     */
    public static final int BUTTON_WIDHT = 250;
    /**
     * Inset offset.
     */
    public static final int INSET_OS = 10;
    /**
     * Four players game.
     */
    public static final int FOUR_PLAYERS_GAME = 4;
    /**
     * Two players game.
     */
    public static final int TWO_PLAYERS_GAME = 2;
    /**
     * Number of the pawns for each player.
     */
    public static final int PLAYER_PAWNS = 4;
    /**
     * Number of cells of the board.
     */
    public static final int CELLS_NUMBER = 15;

    private Constants() {

    }
}
