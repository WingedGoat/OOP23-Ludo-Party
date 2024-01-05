package view;

import java.util.Map;
import java.util.HashMap;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.CellImpl;
import model.api.Cell;
import utility.Constants;
import utility.Position;

/**
 * Board Scene represents the third Scene 
 * contained by the Stage. It's the view
 * with the effective board of the game.
 */
public class BoardScene extends Scene {

    private static final int BOARD_CENTRAL_PANEL_WIDTH = 600;
    private static final int BORDER_WIDTH = 1;
    private static final int BOARD_SIDEPANEL_WIDTH = 220;
    private static final int CELL_WIDTH = 20;
    private final Map<Button, Cell> cells = new HashMap<>();

    /**
     * Constructor.
     * @param stage the stage
     * @param playerName the player name
     */
    public BoardScene(final Stage stage, final String playerName) {
        super(new BorderPane());
        stage.setScene(this);
        stage.setTitle("Board");

        // borderpane - container
        final BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setMinSize(BOARD_CENTRAL_PANEL_WIDTH, BOARD_CENTRAL_PANEL_WIDTH);
        borderPane.setPadding(new Insets(Constants.INSET_OS));

        final var border = new Border(new BorderStroke(
                Color.valueOf("#202020"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH)));

        // gridpane - central panel
        final GridPane centralPane = new GridPane();
        centralPane.setMinSize(BOARD_CENTRAL_PANEL_WIDTH, BOARD_CENTRAL_PANEL_WIDTH);
        createBoard(centralPane);
        //centralPane.add(new Button("central"), 10, 10);
        centralPane.setBorder(border);
        borderPane.setCenter(centralPane);

        // vboxes - lateral panels for Players
        final VBox vBoxLeft = new VBox();
        vBoxLeft.setPrefWidth(BOARD_SIDEPANEL_WIDTH); //FIXME
        vBoxLeft.setBorder(border);
        borderPane.setLeft(vBoxLeft);

        final VBox vBoxRight = new VBox();
        vBoxRight.setPrefWidth(BOARD_SIDEPANEL_WIDTH); //FIXME
        vBoxRight.setBorder(border);
        borderPane.setRight(vBoxRight);

        // hbox - bottom panel for Player Bonus/Malus
        final Button button = new Button(playerName);
        final HBox bottomPane = new HBox(button);
        bottomPane.setPrefHeight(Constants.BOARD_BOTTOM_HEIGHT);
        bottomPane.setBorder(border);
        borderPane.setBottom(bottomPane);

        this.setFill(Color.valueOf("0077b6"));

        stage.show();

        stage.setOnCloseRequest(e -> {
            //System.exit(0);
        });
    }

    private void createBoard(final GridPane panel) {
        for (int i = 0; i < Constants.BOARD_CELLS; i++) {
            for (int j = 0; j < Constants.BOARD_CELLS; j++) {
                final Button bt = new Button(" ");
                bt.setPrefSize(CELL_WIDTH, CELL_WIDTH);
                this.cells.put(bt, new CellImpl(new Position(j, i)));
                //bt.addActionListener(al);
                panel.add(bt, CELL_WIDTH, CELL_WIDTH);
            }
        }
    }

}
