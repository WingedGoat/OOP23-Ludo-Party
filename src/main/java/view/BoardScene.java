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
    private static final int CELL_WIDTH = 40;
    private final Map<Button, Cell> cells = new HashMap<>();
    private static final int BORDER_POINT = 0; // The border of one of the coords of the board
    private static final int FIRST_HALF = 6;
    private static final int MIDDLE_POINT = 7; // The center of a coordinate of the board
    private static final int SECOND_HALF = 8;
    private static final Map<Integer, String> COLOR_CODES = new HashMap<>(// FIXME
            Map.of(0, "#ff0000", 1, "#00ff00", 2, "#0000ff", 3, "#ffff00")); // Color codes to display
    private static final int NO_COLOR = 4;

    /**
     * Constructor.
     * 
     * @param stage      the stage
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
        // centralPane.add(new Button("central"), 10, 10);
        centralPane.setBorder(border);
        borderPane.setCenter(centralPane);

        // vboxes - lateral panels for Players
        final PlayerPanel vBoxLeft = new PlayerPanel(new Button("Roll dice"));
        vBoxLeft.setPrefWidth(BOARD_SIDEPANEL_WIDTH); //FIXME
        vBoxLeft.setBorder(border);
        borderPane.setLeft(vBoxLeft);

        final VBox vBoxRight = new VBox();
        vBoxRight.setPrefWidth(BOARD_SIDEPANEL_WIDTH); // FIXME
        vBoxRight.setBorder(border);
        borderPane.setRight(vBoxRight);

        // hbox - bottom panel for Player Bonus/Malus
        final Button playerButton = new Button(playerName);
        final HBox bottomPane = new HBox(playerButton);
        bottomPane.setPrefHeight(Constants.BOARD_BOTTOM_HEIGHT);
        bottomPane.setBorder(border);
        borderPane.setBottom(bottomPane);

        this.setFill(Color.valueOf("0077b6"));

        stage.show();

        stage.setOnCloseRequest(e -> {
            // System.exit(0);
        });
    }

    private void createBoard(final GridPane panel) {
        for (int i = 0; i < Constants.BOARD_CELLS; i++) {
            for (int j = 0; j < Constants.BOARD_CELLS; j++) {
                final Button bt = new Button(" ");
                bt.setPrefSize(CELL_WIDTH, CELL_WIDTH);
                bt.setDisable(true);
                this.cells.put(bt, new CellImpl(new Position(j, i)));
                // bt.addActionListener(al);
                if (colorNumber(i, j) != NO_COLOR) {
                    bt.setStyle("-fx-background-color: " + COLOR_CODES.get(colorNumber(i, j)) + ";");
                }
                panel.add(bt, j, i);
            }
        }
    }

    private int colorNumber(final int i, final int j) {
        if (i < FIRST_HALF && j < FIRST_HALF || i == MIDDLE_POINT && j > BORDER_POINT && j < MIDDLE_POINT
                || i == FIRST_HALF && j == 1) {
            return 0;
        } else if (i < FIRST_HALF && j > SECOND_HALF || j == MIDDLE_POINT && i > BORDER_POINT && i < MIDDLE_POINT
                || i == 1 && j == SECOND_HALF) {
            return 1;
        } else if (i > SECOND_HALF && j < FIRST_HALF
                || j == Constants.BOARD_CELLS - MIDDLE_POINT - 1 && i < Constants.BOARD_CELLS - BORDER_POINT - 1
                        && i > MIDDLE_POINT
                || i == Constants.BOARD_CELLS - BORDER_POINT - 2 && j == FIRST_HALF) {
            return 2;
        } else if (i > SECOND_HALF && j > SECOND_HALF
                || i == Constants.BOARD_CELLS - MIDDLE_POINT - 1 && j < Constants.BOARD_CELLS - BORDER_POINT - 1
                        && j > MIDDLE_POINT
                || j == Constants.BOARD_CELLS - BORDER_POINT - 2 && i == SECOND_HALF) {
            return 3;
        }
        return 4;
    }

    /**
     * Return PlayerPanel.
     * @return PlayerPanel.
     */
    public PlayerPanel getPlayerPanel() {
        return (PlayerPanel) ((BorderPane) this.getRoot()).getLeft();
    }
}
