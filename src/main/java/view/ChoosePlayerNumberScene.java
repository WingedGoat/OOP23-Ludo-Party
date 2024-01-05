package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import controller.ControllerImpl;
import utility.Constants;

/**
 * Choose Player Number Scene represents the second Scene 
 * contained by the Stage.
 */
public class ChoosePlayerNumberScene extends Scene {
    /**
     * Button width.
     */
    private static final int BUTTON_WIDTH = 300;
    /**
     * Button height.
     */
    private static final int BUTTON_HEIGHT = 150;
    /**
     * Number of players(2).
     */
    private static final int PLAYERS_NUM_2 = 2;
    /**
     * Number of players(4).
     */
    private static final int PLAYERS_NUM_4 = 4;


    /**
     * Constructor.
     * @param stage the stage
     * @param playerName the player name
     */
    public ChoosePlayerNumberScene(final Stage stage, final String playerName) {
        super(new VBox());
        stage.setTitle("Ludo");

        // vbox
        final VBox vbox = (VBox) this.getRoot();
        vbox.setMinSize(Constants.HOME_WINDOW_WIDTH, Constants.HOME_WINDOW_HEIGHT);
        vbox.setPadding(new Insets(Constants.INSET_OS));
        vbox.setSpacing(8);
        vbox.setAlignment(Pos.CENTER);

        // buttons
        final Button twoPlayersBt = new Button(PLAYERS_NUM_2 + " players");
        twoPlayersBt.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        twoPlayersBt.setCursor(Cursor.HAND);
        twoPlayersBt.setOnAction(event -> {
            stage.close();
            new ControllerImpl(stage, playerName, PLAYERS_NUM_2);
        });

        final Button fourPlayersBt = new Button(PLAYERS_NUM_4 + " players");
        fourPlayersBt.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        fourPlayersBt.setCursor(Cursor.HAND);
        fourPlayersBt.setOnAction(event -> {
            stage.close();
            new ControllerImpl(stage, playerName, PLAYERS_NUM_4);
        });

        vbox.getChildren().add(twoPlayersBt);
        vbox.getChildren().add(fourPlayersBt);

        this.setFill(Color.valueOf("0077b6"));
    }

}
