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
 * Second Scene represents the Scene contained by 
 * the Second Stage.
 */
public class SecondScene extends Scene {
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
    public SecondScene(final Stage stage, final String playerName) {
        super(new VBox());
        stage.setTitle("Ludo");

        final VBox vbox = (VBox) this.getRoot();
        vbox.setMinSize(Constants.HOME_WINDOW_WIDTH, Constants.HOME_WINDOW_HEIGHT);
        vbox.setPadding(new Insets(Constants.INSET_OS));
        vbox.setSpacing(8);
        vbox.setAlignment(Pos.CENTER);

        final Button twoPlayersBt = createButton(PLAYERS_NUM_2, playerName, stage);
        twoPlayersBt.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        final Button fourPlayersBt = createButton(PLAYERS_NUM_4, playerName, stage);
        fourPlayersBt.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        vbox.getChildren().add(twoPlayersBt);
        vbox.getChildren().add(fourPlayersBt);

        this.setFill(Color.valueOf("0077b6"));
    }

    private Button createButton(final int playersNumber, final String playerName, final Stage stage) {
        final Button button = new Button(playersNumber + " players");

        button.setOnAction((event) -> {
            stage.close();
            new ControllerImpl(playerName, playersNumber);
        });
        button.setCursor(Cursor.HAND);

        return button;
    }

}
