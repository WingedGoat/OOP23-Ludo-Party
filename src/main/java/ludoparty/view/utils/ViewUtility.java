package ludoparty.view.utils;

import ludoparty.controller.api.Controller;
import ludoparty.view.BoardScene;
import ludoparty.view.ChoosePlayerNumberScene;
import ludoparty.view.SaveScoreStage;
import ludoparty.view.StartScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class used to create the views of the game.
 */
public final class ViewUtility {
    /**
     * The resources folder root.
     */
    public static final String RESOURCES_ROOT_PATH = "ludoparty/";
    /**
     * Home window width.
     */
    public static final int HOME_WINDOW_WIDTH = 480;
    /**
     * Home window height.
     */
    public static final int HOME_WINDOW_HEIGHT = 600;
    /**
     * Board bottom height.
     */
    public static final int BOARD_BOTTOM_HEIGHT = 150;
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
    /**
     * The radius of the circle.
     */
    public static final int CIRCLE_RADIUS = 18;
    /**
     * Radius decrease rate based on the player's number.
     */
    public static final int RADIUS_DECREASE = 2;
    /**
     * The radius multipler.
     */
    public static final double RADIUS_TO_DIAMETER = 2;
    /**
     * The highest point of a pawn in a cell.
     */
    public static final int TOP_ROW = -15;

    private ViewUtility() {
    }

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
     * @param stage      the stage
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
     * @param stage      the stage
     * 
     * @return a new BoardScene
     */
    public static BoardScene createBoardScene(final Controller controller, final Stage stage) {
        return new BoardScene(controller, stage);
    }

    /**
     * Creates the scene used to save the players score.
     * 
     * @param controller the controller
     * @return a new SaveScoreStage
     */
    public static SaveScoreStage createSaveScoreStage(final Controller controller) {
        return new SaveScoreStage(controller);
    }

}
