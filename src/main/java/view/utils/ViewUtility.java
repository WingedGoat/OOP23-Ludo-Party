package view.utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.api.Controller;
import view.BoardScene;
import view.ChoosePlayerNumberScene;
import view.StartScene;

/**
 * Utility class used to create the views of the game.
 */
public final class ViewUtility {
    /**
     * The file separator.
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * The resources folder.
     */
    public static final String RESOURCES_FOLDER = "." + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;
    /**
     * The images folder.
     */
    public static final String IMAGES_FOLDER = "." + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "images" + FILE_SEPARATOR;
    /**
     * The dices folder.
     */
    public static final String DICES_FOLDER = IMAGES_FOLDER + "dices" + FILE_SEPARATOR;
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
     * Button font size.
     */
    public static final int BUTTON_FONT_SIZE = 14;
    /**
     * Inset offset.
     */
    public static final int INSET_OS = 10;
    /**
     * Cell width.
     */
    public static final int CELL_WIDTH = 40;

    private ViewUtility() { }

    /**
     * Creates the app stage.
     */
    public static void createStage() {
        final Stage stage = new Stage();
        stage.setTitle("Ludo Party");
        stage.setResizable(false);
        stage.setScene(ViewUtility.createStartScene(stage));
        stage.show();
    }

    /**
     * Creates the initial scene with the logo and
     * the field in wich the player can insert his/her name.
     * 
     * @param stage the stage
     * 
     * @return a new StartScene
     */
    public static Scene createStartScene(final Stage stage) {
        return new StartScene(stage);
    }

    /**
     * Creates the second scene, the one in which
     * the player can choose the number of players in the game.
     * 
     * @param stage the stage
     * @param playerName the player name
     * 
     * @return a new ChoosePlayerNumberScene
     */
    public static Scene createChoosePlayerScene(final Stage stage, final String playerName) {
        return new ChoosePlayerNumberScene(stage, playerName);
    }

    /**
     * Creates the third scene, the one with the board of the game.
     * 
     * @param controller the controller
     * @param stage the stage
     * 
     * @return a new BoardScene
     */
    public static BoardScene createBoardScene(final Controller controller, final Stage stage) {
        return new BoardScene(controller, stage);
    }

}
