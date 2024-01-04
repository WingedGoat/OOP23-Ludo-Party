package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.Constants;
/**
 * View with the effective board of the game.
 */
public class BoardScene extends Scene {

    /**
     * Constructor.
     * @param stage
     *          the stage
     */
    public BoardScene(final Stage stage) {
        super(new BorderPane());
        final BorderPane layout = (BorderPane) this.getRoot();
        final Pane pane = new Pane();
        pane.setPrefWidth(Constants.HOME_WINDOW_HEIGHT);
        pane.setPrefHeight(Constants.HOME_WINDOW_HEIGHT);
 
        layout.setCenter(pane);
        //layout.setBottom(PlayerBonus/Malus);
        final Scene scene = new Scene(layout);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(e -> {
            //System.exit(0);
        });
    }
}
