package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import controller.api.Controller;

/**
 * Utility class used to create the views of the game.
 */
public final class ViewUtility {

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
    public static Scene createBoardScene(final Controller controller, final Stage stage) {
        return new BoardScene(controller, stage);
    }

}
