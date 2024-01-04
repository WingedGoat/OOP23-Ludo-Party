package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import model.api.Player;
import utility.Constants;
/**
 * View with the effective board of the game.
 */
@SuppressWarnings("PMD")
public class BoardView extends Stage {

    /**
     * Constructor.
     * @param player 
     *              the player
     * @param playersNumber 
     *              the number of players in the game
     */
    public BoardView(final Player player, final int playersNumber) {
        final BorderPane layout = new BorderPane();
        final Pane pane = new Pane();
        pane.setPrefWidth(Constants.HOME_WINDOW_HEIGHT);
        pane.setPrefHeight(Constants.HOME_WINDOW_HEIGHT);
 
        layout.setCenter(pane);
        //layout.setBottom(PlayerBonus/Malus);
        final Scene scene = new Scene(layout);
        this.setScene(scene);

        this.setResizable(false);
        this.show();

        this.setOnCloseRequest(e -> {
            //System.exit(0);
        });
    }
}
