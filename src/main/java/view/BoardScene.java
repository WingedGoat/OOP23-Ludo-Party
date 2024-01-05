package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.Constants;

/**
 * Board Scene represents the third Scene 
 * contained by the Stage. It's the view
 * with the effective board of the game.
 */
public class BoardScene extends Scene {

    private static final int BORDER_WIDTH = 3;

    /**
     * Constructor.
     * @param stage the stage
     */
    public BoardScene(final Stage stage) {
        super(new BorderPane());
        stage.setScene(this);
        stage.setTitle("Board");

        // borderpane - container
        final BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setMinSize(Constants.HOME_WINDOW_HEIGHT, Constants.HOME_WINDOW_HEIGHT);
        borderPane.setPadding(new Insets(Constants.INSET_OS));

        // gridpane - central panel
        final GridPane centralPane = new GridPane();
        centralPane.add(new Button("central"), 10, 10);
        borderPane.setCenter(centralPane);

        // hbox - bottom panel for Player Bonus/Malus
        final Button button = new Button("Button Bottom");
        final HBox bottomPane = new HBox(button);
        bottomPane.setPrefHeight(Constants.BOARD_BOTTOM_HEIGHT);
        bottomPane.setBorder(new Border(new BorderStroke(
                Color.CHOCOLATE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH))));
        borderPane.setBottom(bottomPane);

        this.setFill(Color.valueOf("0077b6"));

        stage.show();

        stage.setOnCloseRequest(e -> {
            //System.exit(0);
        });
    }

}
