package view;

import java.io.File;
import java.nio.file.Path;

import controller.api.Controller;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utility.Position;

/**
 * The side panel with the player info.
 */
public final class PlayerPanel extends AnchorPane {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String IMAGE_PATH = Path.of("." + FILE_SEPARATOR + "resources" + FILE_SEPARATOR
        + "images" + FILE_SEPARATOR + "dices" + FILE_SEPARATOR + "dice-1.png").toString();
    private static final int PANE_HEIGHT = 600;
    // circle props
    private static final int CIRCLE_RADIUS = 80;
    private static final int INNER_CIRCLE_RADIUS = 77;
    private static final String CIRCLE_COLOR = "#555555";
    private static final String INNER_CIRCLE_COLOR = "#F1F1F1";
    // components positions
    private static final int LABEL_X_LAYOUT = 60;
    private static final int LABEL_TOP_NAME_Y_LAYOUT = 230;
    private static final int LABEL_TOP_COINS_Y_LAYOUT = 250;
    private static final int LABEL_BOTTOM_NAME_Y_LAYOUT = 350;
    private static final int LABEL_BOTTOM_COINS_Y_LAYOUT = 370;

    private static final int DICE_WIDTH = 40;
    private static final int DICE_HEIGHT = 40;
//    private static final int DICE_X_LAYOUT = 120;
//    private static final int DICE_TOP_Y_LAYOUT = 210;
//    private static final int DICE_BOTTOM_Y_LAYOUT = 340;

    private static final int CENTOSETTANTA = 170;
    //private static final int DUEEVENTI = 220;
    private static final int TREEQUARANTA = 340;
    private static final int DUEESESSANTA = 260;

    private final String playerTopName;
    private final String playerBottomName;

    /**
     * Constructor.
     * 
     * @param controller the controller
     */
    public PlayerPanel(final Controller controller) {
        this.playerTopName = "AA";
        this.playerBottomName = "BB";

        this.setPrefHeight(PANE_HEIGHT);

        final Position bottomPos = new Position(110, 480);
        final Position topPos = new Position(110, 120);
        final Group g1 = createBottomLeftPlayer(bottomPos, controller);
        final Group g2 = createTopRightPlayer(topPos, controller);

        //add bottom LEFT group and top RIGHT group
        this.getChildren().addAll(g1, g2);

        if (controller.getGame().getPlayers().size() > 2) {
            //add also top RIGHT group
            //and bottom LEFT group
            final Group g3 = createTopLeftPlayer(topPos, controller);
            final Group g4 = createBottomRightPlayer(bottomPos, controller);
            this.getChildren().addAll(g3, g4);
        }
    }

    /**
     * AA.
     * @return A
     */
    public String getPlayerTopName() {
        return playerTopName;
    }

    /**
     * BB.
     * @return B
     */
    public String getPlayerBottomName() {
        return playerBottomName;
    }

    // HUMAN PLAYER (in BOTTOM LEFT position)
    private Group createBottomLeftPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = new Label(ctrl.getGame().getHumanPlayer().getName());
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);

        final Label playerCoins = new Label("Ludollari: " + ctrl.getGame().getHumanPlayer().getCoins());
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_BOTTOM_COINS_Y_LAYOUT);

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(TREEQUARANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            //controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

    // COMPUTER PLAYER (in TOP RIGHT position)
    private Group createTopRightPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        String name = ctrl.getGame().getPlayers().get(1).getName();
        if (ctrl.getPlayersNumber() > 2) {
            name = ctrl.getGame().getPlayers().get(2).getName();
        }
        final Label playerName = createNameLabelTopPanel(name);

        int coins = ctrl.getGame().getPlayers().get(1).getCoins();
        if (ctrl.getPlayersNumber() > 2) {
            coins = ctrl.getGame().getPlayers().get(2).getCoins();
        }
        final Label playerCoins = createCoinLabelTopPanel(coins);

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(DUEESESSANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            //controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }


    // COMPUTER PLAYER (in RIGHT TOP position) [g3]
    private Group createTopLeftPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = createNameLabelTopPanel(ctrl.getGame().getPlayers().get(2).getName());
        final Label playerCoins = createCoinLabelTopPanel(ctrl.getGame().getPlayers().get(2).getCoins());

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(DUEESESSANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            // controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }



    // COMPUTER PLAYER (in BOTTOM TOP position) [g4]
    private Group createBottomRightPlayer(final Position pos, final Controller ctrl) {
        final Group g = new Group();

        final Circle playerAvatar = createPlayerAvatar(pos);
        final Circle playerAvatarInner = createPlayerInnerAvatar(pos);

        final Label playerName = new Label(ctrl.getGame().getPlayers().get(3).getName());
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);

        final Label playerCoins = new Label("Ludollari: " + ctrl.getGame().getPlayers().get(3).getCoins());
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_BOTTOM_COINS_Y_LAYOUT);

        AnchorPane.setLeftAnchor(playerName, 0.0);
        AnchorPane.setRightAnchor(playerName, 0.0);

        AnchorPane.setLeftAnchor(playerCoins, 0.0);
        AnchorPane.setRightAnchor(playerCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(DUEESESSANTA);

        diceImage.setOnMouseClicked(mouseEvent -> {
            // controller.get
        });

        g.getChildren().addAll(playerAvatar, playerAvatarInner, playerName, playerCoins, diceImage);

        return g;
    }

    private Circle createPlayerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
    }

    private Circle createPlayerInnerAvatar(final Position pos) {
        return new Circle(pos.getX(), pos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));
    }

    private Label createNameLabelTopPanel(final String name) {
        final Label playerName = new Label(name);
        playerName.setLayoutX(LABEL_X_LAYOUT);
        playerName.setLayoutY(LABEL_TOP_NAME_Y_LAYOUT);

        return playerName;
    }

    private Label createCoinLabelTopPanel(final int coins) {
        final Label playerCoins = new Label("Ludollari: " + coins);
        playerCoins.setLayoutX(LABEL_X_LAYOUT);
        playerCoins.setLayoutY(LABEL_TOP_COINS_Y_LAYOUT);

        return playerCoins;
    }

    /**
     * Creates and return the group displayed on top of the panel.
     * The group contains the player's avatar, its name and the 
     * amount of coins picked up durign the game.
     * 
     * @return the top Group

    private Group getTopPlayerGroup() {
        final Group g = new Group();

        final Position topPos = new Position(110, 120);

        final Circle playerAvatarTop = new Circle(topPos.getX(), topPos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
        final Circle playerAvatarTopInner = 
            new Circle(topPos.getX(), topPos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));

        final Label playerTopName = new Label(this.playerTopName);
        playerTopName.setLayoutX(LABEL_X_LAYOUT);
        playerTopName.setLayoutY(LABEL_TOP_NAME_Y_LAYOUT);

        final Label playerTopCoins = new Label("Ludollari: 0");
        playerTopCoins.setLayoutX(LABEL_X_LAYOUT);
        playerTopCoins.setLayoutY(LABEL_TOP_COINS_Y_LAYOUT);

        AnchorPane.setLeftAnchor(playerTopName, 0.0);
        AnchorPane.setRightAnchor(playerTopName, 0.0);

        AnchorPane.setLeftAnchor(playerTopCoins, 0.0);
        AnchorPane.setRightAnchor(playerTopCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(DUEEVENTI);

        diceImage.setOnMouseClicked(mouseEvent -> {
            //controller.get
        });

        g.getChildren().addAll(playerAvatarTop, playerAvatarTopInner, playerTopName, playerTopCoins, diceImage);

        return g;
    }
     */
    /**
     * Creates and return the group displayed at the bottom of the panel.
     * The group contains the player's avatar, its name and the 
     * amount of coins picked up durign the game.
     * 
     * @return the bottom Group

    private Group getBottomPlayerGroup() {
        final Group g = new Group();

        final Position bottomPos = new Position(110, 480);

        final Circle playerAvatarBottom = 
            new Circle(bottomPos.getX(), bottomPos.getY(), CIRCLE_RADIUS, Color.valueOf(CIRCLE_COLOR));
        final Circle playerAvatarBottomInner = 
            new Circle(bottomPos.getX(), bottomPos.getY(), INNER_CIRCLE_RADIUS, Color.valueOf(INNER_CIRCLE_COLOR));

        final Label playerBottomName = new Label(this.playerBottomName);
        playerBottomName.setLayoutX(LABEL_X_LAYOUT);
        playerBottomName.setLayoutY(LABEL_BOTTOM_NAME_Y_LAYOUT);
        //playerBottomName.setAlignment(Pos.CENTER);

        final Label playerBottomCoins = new Label("Ludollari: 0");
        playerBottomCoins.setLayoutX(LABEL_X_LAYOUT);
        playerBottomCoins.setLayoutY(LABEL_BOTTOM_COINS_Y_LAYOUT);
        //playerBottomName.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(playerBottomName, 0.0);
        AnchorPane.setRightAnchor(playerBottomName, 0.0);

        AnchorPane.setLeftAnchor(playerBottomCoins, 0.0);
        AnchorPane.setRightAnchor(playerBottomCoins, 0.0);

        // dice
        final ImageView diceImage = createDicImageView();
        diceImage.setLayoutX(CENTOSETTANTA);
        diceImage.setLayoutY(340);

        diceImage.setOnMouseClicked(mouseEvent -> {
            //controller.get
        });

        g.getChildren().addAll(playerAvatarBottom, playerAvatarBottomInner, playerBottomName, playerBottomCoins, diceImage);

        return g;
    }
     */
    private ImageView createDicImageView() {
        final ImageView diceImage = new ImageView();

        final File file =  new File(IMAGE_PATH);
        diceImage.setImage(new Image(file.toURI().toString()));
        diceImage.setFitHeight(DICE_HEIGHT);
        diceImage.setFitWidth(DICE_WIDTH);

        final Glow glow = new Glow(.8);
        diceImage.setOnMouseEntered(mouseEvent -> {
            diceImage.setEffect(glow);
            diceImage.setCursor(Cursor.HAND);
        });
        diceImage.setOnMouseExited(mouseEvent -> {
            diceImage.setEffect(null);
        });

        return diceImage;
    }

}
