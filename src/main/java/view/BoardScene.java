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
import javafx.stage.Stage;
import model.PlayerHome;
import model.Position;
import model.PlayerHome.HomePosition;
import model.PlayerSafePath;
import controller.api.Controller;
import utility.BColor;
import utility.Constants;

/**
 * Board Scene represents the third Scene
 * contained by the Stage. It's the view
 * with the effective board of the game.
 */
public class BoardScene extends Scene {

    private static final int BOARD_CENTRAL_PANEL_WIDTH = 600;
    private static final int BOARD_SIDEPANEL_WIDTH = 220;
    private static final int CELL_WIDTH = 40;

    private static final double BORDER_WIDTH = 0.5;
    private static final String BG_COLOR_CSS = "-fx-background-color: ";
    private static final String BG_RADIUS_CSS = "; -fx-border-color: #5A5858; -fx-border-width: 0.3px; "
        + "-fx-background-radius: 0";

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
        borderPane.setMinSize(BOARD_CENTRAL_PANEL_WIDTH, BOARD_CENTRAL_PANEL_WIDTH);
        borderPane.setPadding(new Insets(Constants.INSET_OS));

        final var border = new Border(new BorderStroke(
                Color.valueOf("#202020"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(BORDER_WIDTH)));

        // gridpane - central panel
        final GridPane centralPane = new GridPane();
        centralPane.setMinSize(BOARD_CENTRAL_PANEL_WIDTH, BOARD_CENTRAL_PANEL_WIDTH);
        createBoard(centralPane);
        centralPane.setBorder(border);
        borderPane.setCenter(centralPane);

        // lateral panels for Players
        final PlayerPanelLeft leftPane = new PlayerPanelLeft(controller);
        leftPane.setPrefWidth(BOARD_SIDEPANEL_WIDTH);
        borderPane.setLeft(leftPane);

        final PlayerPanelRight rightPane = new PlayerPanelRight(controller);
        rightPane.setPrefWidth(BOARD_SIDEPANEL_WIDTH);
        borderPane.setRight(rightPane);

        // hbox - bottom panel for Player Bonus/Malus
        final Button playerButton = new Button("ciao");
        playerButton.setDisable(true);
        final HBox bottomPane = new HBox(playerButton);
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

    // private void createBoard(final GridPane panel, final Controller controller) {
    private void createBoard(final GridPane panel) {
        /*
         * final EventHandler<ActionEvent> buttonClicked = new EventHandler<>() {
         * 
         * @Override
         * public void handle(final ActionEvent e) {
         * //final Button clicked = (Button) e.getSource();
         * if (controller.clickBoardButton(clicked)) {
         * borderPane.requestFocus();
         * }
         * }
         * };
         */
        for (int i = 0; i < Constants.BOARD_CELLS; i++) {
            for (int j = 0; j < Constants.BOARD_CELLS; j++) {
                final Button bt = new Button(" ");
                bt.setStyle("-fx-background-color: #fdfcfc;"
                        + "-fx-border-color: #5A5858; -fx-border-width: 0.5px; " + BG_RADIUS_CSS);
                //bt.setOnAction(e -> System.out.println("-clickato"));

                final Position pos = new Position(i, j);

                if (PlayerHome.getPlayerHome(HomePosition.BOTTOM_LEFT).contains(pos) 
                    || PlayerSafePath.getPath(HomePosition.BOTTOM_LEFT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.BLUE.get() + BG_RADIUS_CSS);
                }
                if (PlayerHome.getPlayerHome(HomePosition.TOP_LEFT).contains(pos) 
                    || PlayerSafePath.getPath(HomePosition.TOP_LEFT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.RED.get() + BG_RADIUS_CSS);
                }
                if (PlayerHome.getPlayerHome(HomePosition.TOP_RIGHT).contains(pos) 
                    || PlayerSafePath.getPath(HomePosition.TOP_RIGHT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.GREEN.get() + BG_RADIUS_CSS);
                }
                if (PlayerHome.getPlayerHome(HomePosition.BOTTOM_RIGHT).contains(pos) 
                    || PlayerSafePath.getPath(HomePosition.BOTTOM_RIGHT).contains(pos)) {
                    bt.setStyle(BG_COLOR_CSS + BColor.YELLOW.get() + BG_RADIUS_CSS);
                }
                bt.setPrefSize(CELL_WIDTH, CELL_WIDTH);
                bt.setCursor(Cursor.HAND);

                // this.cells.put(bt, new CellImpl());
                panel.add(bt, j, i);
            }
        }
    }

}
