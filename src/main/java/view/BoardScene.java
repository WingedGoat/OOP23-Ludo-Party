package view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import model.Position;
import model.api.Player;
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
    private static final int PAWN_POSITION = 60;
    private static final int CIRCLE_RADIUS = 18;

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
        createBoard(controller);
        initPawns(controller);
        boardPanel.setMinSize(BOARD_PANEL_WIDTH, BOARD_PANEL_WIDTH);
        boardPanel.setBorder(border);
        borderPane.setCenter(boardPanel);

        // lateral panels for the players
        final PlayerPanelLeft leftPane = new PlayerPanelLeft(controller);
        leftPane.setPrefWidth(PLAYER_PANEL_WIDTH);
        borderPane.setLeft(leftPane);

        final PlayerPanelRight rightPane = new PlayerPanelRight(controller);
        rightPane.setPrefWidth(PLAYER_PANEL_WIDTH);
        borderPane.setRight(rightPane);

        // hbox - bottom panel for Player Bonus/Malus and Shop
        final InventoryPane inventoryPane = new InventoryPane(/*controller*/);
        final ShopPane shopPane = new ShopPane(controller);
        final BorderPane bottomPane = new BorderPane();
        bottomPane.setTop(inventoryPane);
        bottomPane.setBottom(shopPane);

        bottomPane.setPrefHeight(Constants.BOARD_BOTTOM_HEIGHT);
        bottomPane.setBorder(border);

        borderPane.setBottom(bottomPane);

        this.setFill(Color.valueOf("0077b6"));

        // when finish the turn
        this.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) { // && controller.pressEnterKey()) {
                for (int i = 1; i < controller.getPlayersNumber(); i++) {
                    controller.getGame().getTurn().passTurnTo(controller.getGame().getPlayers().get(i));
                    ImageView diceImage = null;
                    if (controller.getPlayersNumber() == 2) {
                        diceImage = (ImageView) ((Group) (rightPane.getChildren().get(0))).getChildren().get(4);
                    } else {
                        switch (i) {
                            case 1:
                            diceImage = (ImageView) ((Group) (leftPane.getChildren().get(1))).getChildren().get(4);
                            break;
                            case 2:
                            diceImage = (ImageView) ((Group) (rightPane.getChildren().get(0))).getChildren().get(4);
                            break;
                            default:
                            diceImage = (ImageView) ((Group) (rightPane.getChildren().get(1))).getChildren().get(4);
                            break;
                        }
                    }
                    final int diceResult = controller.getGame().getTurn().getCurrentPlayer().rollDice();
                    leftPane.showDiceNumber(diceImage, diceResult);
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
        borderPane.requestFocus();

        stage.setOnCloseRequest(e -> {
            // System.exit(0);
        });
    }

    /**
     * Creates the game board, with the colored houses,
     * safe paths for each player, and the white path.
     * 
     * @param ctrl the controller
     */
    private void createBoard(final Controller ctrl) {
        LOGGER.error("log something");

        for (int i = 0; i < Constants.CELLS_NUMBER; i++) {
            for (int j = 0; j < Constants.CELLS_NUMBER; j++) {
                final Button bt = new Button(" ");
                bt.setStyle("-fx-background-color: #fdfcfc;"
                        + "-fx-border-color: #5A5858; -fx-border-width: 0.5px; " + BG_RADIUS_CSS);
                // bt.setOnAction(e -> System.out.println("-clickato"));

                final Position pos = new Position(i, j);

                if (ctrl.getGame().getBoard().getBottomLeftHouse().contains(pos)
                        || ctrl.getGame().getBoard().getBottomLeftSafePath().contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.BLUE.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (ctrl.getGame().getBoard().getTopLeftHouse().contains(pos)
                        || ctrl.getGame().getBoard().getTopLeftSafePath().contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.RED.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (ctrl.getGame().getBoard().getTopRightHouse().contains(pos)
                        || ctrl.getGame().getBoard().getTopRightSafePath().contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.GREEN.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                } else if (ctrl.getGame().getBoard().getBottomRightHouse().contains(pos)
                        || ctrl.getGame().getBoard().getBottomRighSafePath().contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.YELLOW.get() + BG_RADIUS_CSS);
                    bt.setOnMouseEntered(e -> bt.setCursor(null));
                }

                bt.setPrefSize(CELL_WIDTH, CELL_WIDTH);
                bt.setCursor(Cursor.HAND);

                this.boardPanel.add(bt, j, i);
            }
        }
    }

    /**
     * Sets the pawn in the initial position in each player home.
     * 
     * @param controller
     */
    private void initPawns(final Controller controller) {

        for (final Player player : controller.getGame().getPlayers()) {
            for (int i = 0; i < player.getPawns().size(); i++) {
                final Circle pawn = createPawn(player.getColor());
                pawn.setOnMouseEntered(event -> pawn.setCursor(Cursor.HAND));
                final int index = i;
                pawn.setOnMouseClicked(e -> {
                    controller.getGame().getMovement().move(player.getPawns().get(index),
                        controller.getGame().getTurn().getDiceResult(), controller.getGame());
                    final Circle newPawn = createPawn(player.getColor());
                    final Position newPos = player.getPawns().get(index).getPosition();
                    this.boardPanel.add(newPawn, newPos.getX(), newPos.getY());
                });

                final Position pos = player.getPawns().get(i).getStartPosition();
                this.boardPanel.add(pawn, pos.getY(), pos.getX()); // inverted X and Y

                /*
                model.Movement m = new model.Movement(); (da usare successivamente per
                testare se va)

                pawn.setOnMouseClicked(event -> {
                    // m.move();
                    // LOGGER.error("-- moving pawn");
                    pawn.setTranslateX(controller.getPlayersNumber());
                    pawn.setTranslateY(pawn.getTranslateX() + Index.FIVE);
                    event.consume();
                });
                */
            }
        }
    }

    private Circle createPawn(final BColor color) {
        BColor newColor;

        switch (color) {
            case BLUE:
                newColor = BColor.DARK_BLUE;
                break;
            case RED:
                newColor = BColor.DARK_RED;
                break;
            case GREEN:
                newColor = BColor.DARK_GREEN;
                break;
            case YELLOW:
                newColor = BColor.DARK_YELLOW;
                break;
            default:
                newColor = BColor.GREY;
                break;
        }

        final Circle c = new Circle(PAWN_POSITION, PAWN_POSITION, CIRCLE_RADIUS, Color.valueOf(BColor.DARK_GREY.get()));
        c.setStroke(Color.valueOf(newColor.get()));
        c.setStrokeWidth(3.0);
        return c;
    }

}
