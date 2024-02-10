package view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.PlayerHome;
import model.Position;
import model.PlayerHome.HomePosition;
import model.PlayerSafePath;
import controller.api.Controller;
import utility.BColor;
import utility.Constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Board Scene represents the third Scene
 * contained by the Stage. It's the view
 * with the effective board of the game.
 */
public class BoardScene extends Scene {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final int BOARD_PANEL_WIDTH = 600;
    private static final int PLAYER_PANEL_WIDTH = 220;
    private static final int CELL_WIDTH = 40;
    private static final int CELL_CENTER_POINT = 20;
    private static final int CIRCLE_RADIUS = 14;

    private static final double BORDER_WIDTH = 0.5;
    private static final String BG_COLOR_CSS = "-fx-background-color: ";
    private static final String BG_RADIUS_CSS = "; -fx-border-color: #5A5858; -fx-border-width: 0.3px; "
        + "-fx-background-radius: 0";
    private final GridPane boardPanel;

    /**
     * Constructor.
     * 
     * @param controller the controller
     * @param stage      the stage
     */
    public BoardScene(final Controller controller, final Stage stage) {
        super(new BorderPane());
        stage.setScene(this);
        stage.setTitle("Board");

        // borderpane - container
        final BorderPane borderPane = (BorderPane) this.getRoot();
        borderPane.setMinSize(BOARD_PANEL_WIDTH, BOARD_PANEL_WIDTH);
        borderPane.setPadding(new Insets(Constants.INSET_OS));

        final var border = new Border(new BorderStroke(
                Color.valueOf("#202020"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH)));

        // gridpane - central panel
        boardPanel = new GridPane();
        createBoard();
        initPawns();
        boardPanel.setMinSize(BOARD_PANEL_WIDTH, BOARD_PANEL_WIDTH);
        boardPanel.setBorder(border);
        borderPane.setCenter(boardPanel);

        // lateral panels for Players
        final PlayerPanelLeft leftPane = new PlayerPanelLeft(controller);
        leftPane.setPrefWidth(PLAYER_PANEL_WIDTH);
        borderPane.setLeft(leftPane);

        final PlayerPanelRight rightPane = new PlayerPanelRight(controller);
        rightPane.setPrefWidth(PLAYER_PANEL_WIDTH);
        borderPane.setRight(rightPane);

        // hbox - bottom panel for Player Bonus/Malus
        final Button playerButton = new Button("ciao");
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

        // when finish the turn
        this.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) { // && controller.pressEnterKey()) {
                for (int i = 1; i < controller.getPlayersNumber(); i++) {
                    i = 2;
                    /*
                     * controller.playTurn(i);
                     * vBoxLeft.getDiceLabel().setText(
                     * vBoxLeft.getDiceLabel().getText() + "\n"
                     * + controller.getDiceResult(i)
                     * );
                     */
                }
                // rollDiceButton.requestFocus();
            }
        });

        stage.show();

        stage.setOnCloseRequest(e -> {
            // System.exit(0);
        });
    }

    /**
     * Creates the game board, with the colored houses
     * and the safe path for each player.
     */
    private void createBoard() { // final Controller ctrl

        for (int i = 0; i < Constants.CELLS_NUMBER; i++) {
            for (int j = 0; j < Constants.CELLS_NUMBER; j++) {
                final Button bt = new Button(" ");
                bt.setStyle("-fx-background-color: #fdfcfc;"
                        + "-fx-border-color: #5A5858; -fx-border-width: 0.5px; " + BG_RADIUS_CSS);
                // bt.setOnAction(e -> System.out.println("-clickato"));

                final Position pos = new Position(i, j);

                // player home 1 --
                if (PlayerHome.getPlayerHome(HomePosition.BOTTOM_LEFT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.BLUE.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (PlayerSafePath.getPath(HomePosition.BOTTOM_LEFT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.BLUE.get() + BG_RADIUS_CSS);
                } else if (PlayerHome.getPlayerHome(HomePosition.TOP_LEFT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.RED.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (PlayerSafePath.getPath(HomePosition.TOP_LEFT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.RED.get() + BG_RADIUS_CSS);
                } else if (PlayerHome.getPlayerHome(HomePosition.TOP_RIGHT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.GREEN.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (PlayerSafePath.getPath(HomePosition.TOP_RIGHT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.GREEN.get() + BG_RADIUS_CSS);
                } else if (PlayerHome.getPlayerHome(HomePosition.BOTTOM_RIGHT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.YELLOW.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (PlayerSafePath.getPath(HomePosition.BOTTOM_RIGHT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.YELLOW.get() + BG_RADIUS_CSS);
                }
                bt.setPrefSize(CELL_WIDTH, CELL_WIDTH);
                bt.setCursor(Cursor.HAND);

                // this.cells.put(bt, new CellImpl());
                this.boardPanel.add(bt, j, i);
            }
        }
    }

    /**
     * Sets the pawn in the initial position in each player home.
     */
    private void initPawns() {

        for (int i = 0; i < Constants.CELLS_NUMBER; i++) {
            for (int j = 0; j < Constants.CELLS_NUMBER; j++) {

                //if()

                //Circle pawn = new Circle(CELL_WIDTH * i + CELL_CENTER_POINT, CELL_WIDTH * j + CELL_CENTER_POINT, CIRCLE_RADIUS);
                final Circle pawn = new Circle(CELL_WIDTH + CELL_CENTER_POINT, CELL_WIDTH + CELL_CENTER_POINT, CIRCLE_RADIUS);
                pawn.setOnMouseEntered(event -> pawn.setCursor(Cursor.HAND));

                pawn.setOnMouseDragged(event ->  {
                    LOGGER.error("-- moving pawn");
                    pawn.setTranslateX(event.getX() + pawn.getTranslateX());
                    pawn.setTranslateY(event.getY() + pawn.getTranslateY());
                    event.consume();
                });

                this.boardPanel.add(pawn, 1, 1);
            }
        }
    }

}
