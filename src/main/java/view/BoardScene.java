package view;

import java.util.Map;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
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
import controller.ControllerImpl;
import utility.Constants;

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
    private static final int BORDER_POINT = 0; // The border of one of the coords of the board
    private static final int FIRST_HALF = 6;
    private static final int MIDDLE_POINT = 7; // The center of a coordinate of the board
    private static final int SECOND_HALF = 8;
    private static final Map<Integer, String> COLOR_CODES = new HashMap<>(// FIXME
            Map.of(0, "#ff0000", 1, "#00ff00", 2, "#0000ff", 3, "#ffff00")); // Color codes to display
    private static final int NO_COLOR = 4;
    private final BorderPane borderPane;

    /**
     * Constructor.
     * 
     * @param controller the controller
     * @param stage      the stage
     */
    public BoardScene(final ControllerImpl controller, final Stage stage) {
        super(new BorderPane());
        stage.setScene(this);
        stage.setTitle("Board");

        // borderpane - container
        borderPane = (BorderPane) this.getRoot();
        borderPane.setMinSize(BOARD_CENTRAL_PANEL_WIDTH, BOARD_CENTRAL_PANEL_WIDTH);
        borderPane.setPadding(new Insets(Constants.INSET_OS));

        final var border = new Border(new BorderStroke(
                Color.valueOf("#202020"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH)));

        // gridpane - central panel
        final GridPane centralPane = new GridPane();
        centralPane.setMinSize(BOARD_CENTRAL_PANEL_WIDTH, BOARD_CENTRAL_PANEL_WIDTH);
        createBoard(centralPane, controller);
        centralPane.setBorder(border);
        borderPane.setCenter(centralPane);

        // vboxes - lateral panels for Players
        final Button rollDiceButton = new Button("Tira il dado");
        final PlayerPanel vBoxLeft = new PlayerPanel("Player 2", controller.getHumanPlayerName()); 
            //new PlayerPanel(rollDiceButton, new Label(" "));
        rollDiceButton.setOnAction(e -> {
            if (controller.clickRollDiceButton()) {
                vBoxLeft.getDiceLabel().setText(controller.getDiceResult(0));
            }
            borderPane.requestFocus();
        });
        vBoxLeft.setPrefWidth(BOARD_SIDEPANEL_WIDTH); //FIXME
        //vBoxLeft.setBorder(border);
        borderPane.setLeft(vBoxLeft);

        final AnchorPane vBoxRight = new PlayerPanel("Player 3", "Player 4");
        vBoxRight.setPrefWidth(BOARD_SIDEPANEL_WIDTH); // FIXME
        //vBoxRight.setBorder(border);
        borderPane.setRight(vBoxRight);

        // hbox - bottom panel for Player Bonus/Malus
        final Button playerButton = new Button(controller.getHumanPlayerName());
        playerButton.setDisable(true);
        final Button item1 = new Button();
        final Button item2 = new Button();
        final Button item3 = new Button();
        final HBox bottomPane = new HBox(playerButton, item1, item2, item3);
        final EventHandler<ActionEvent> itemClicked = new EventHandler<>() {

            @Override
            public void handle(final ActionEvent e) {
                final Button clicked = (Button) e.getSource();
                if (!clicked.getText().isEmpty() && controller.clickBonusButton(clicked)) {
                    clicked.setText("");
                    //aggiungere eventuali conseguenze visive dell'avere giocato un bonus su se stessi
                }
                borderPane.requestFocus();
            }

        };
        item1.setOnAction(itemClicked);
        item2.setOnAction(itemClicked);
        item3.setOnAction(itemClicked);
        bottomPane.setPrefHeight(Constants.BOARD_BOTTOM_HEIGHT);
        bottomPane.setBorder(border);
        borderPane.setBottom(bottomPane);

        this.setFill(Color.valueOf("0077b6"));

        this.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER) && controller.pressEnterKey()) {
                for (int i = 1; i < controller.getPlayersNumber(); i++) {
                    controller.playTurn(i);
                    vBoxLeft.getDiceLabel().setText(
                        vBoxLeft.getDiceLabel().getText() + "\n"
                        + controller.getDiceResult(i)
                    );
                }
                rollDiceButton.requestFocus();
            }
        });

        stage.show();

        stage.setOnCloseRequest(e -> {
            // System.exit(0);
        });
    }

    private void createBoard(final GridPane panel, final ControllerImpl controller) {

        final EventHandler<ActionEvent> buttonClicked = new EventHandler<>() {

            @Override
            public void handle(final ActionEvent e) {
                final Button clicked = (Button) e.getSource();
                if (controller.clickBoardButton(clicked)) {
                    borderPane.requestFocus();
                }
            }
        };
        for (int i = 0; i < Constants.BOARD_CELLS; i++) {
            for (int j = 0; j < Constants.BOARD_CELLS; j++) {
                final Button bt = new Button(" ");
                bt.setPrefSize(CELL_WIDTH, CELL_WIDTH);
                bt.setOnAction(buttonClicked);
                controller.addToCells(bt, i, j);
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
