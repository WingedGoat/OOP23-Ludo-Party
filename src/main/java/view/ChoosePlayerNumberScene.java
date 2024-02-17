package view;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import controller.ControllerImpl;
import utils.BColor;
import utils.Constants;
import view.utils.ResourcePath;
import view.utils.ViewUtility;

/**
 * Choose Player Number Scene represents the second Scene 
 * contained by the Stage.
 */
public class ChoosePlayerNumberScene extends Scene {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 150;
    private static final int BUTTON_FONT_SIZE = 18;

    /**
     * Constructor.
     * 
     * @param stage the stage
     * @param playerName the player name
     */
    public ChoosePlayerNumberScene(final Stage stage, final String playerName) {
        super(new VBox());
        stage.setTitle("Ludo");

        // vbox
        final VBox vbox = (VBox) this.getRoot();
        vbox.setMinSize(ViewUtility.HOME_WINDOW_WIDTH, ViewUtility.HOME_WINDOW_HEIGHT);
        vbox.setPadding(new Insets(ViewUtility.INSET_OS));
        vbox.setSpacing(8);
        vbox.setAlignment(Pos.CENTER);

        // players number buttons
        final Button twoPlayersBt = createButton(Constants.PLAYERS_NUM_2);
        twoPlayersBt.setOnAction(event -> {
            stage.close();
            new Thread(new ControllerImpl(stage, playerName, Constants.PLAYERS_NUM_2)).start();
        });

        final Button fourPlayersBt = createButton(Constants.PLAYERS_NUM_4);
        fourPlayersBt.setOnAction(event -> {
            stage.close();
            new Thread(new ControllerImpl(stage, playerName, Constants.PLAYERS_NUM_4)).start();
        });

        vbox.getChildren().add(twoPlayersBt);
        vbox.getChildren().add(fourPlayersBt);

        // background
        final Image img = new Image(new File(ResourcePath.BACKGROUND_IMG.getPath()).toURI().toString()); 
            final BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        vbox.setBackground(new Background(bgImg));

        this.setFill(BColor.LIGHT_BLACK.get());
    }

    private Button createButton(final int playersNumber) {
        final Button bt = new Button(playersNumber + " giocatori");
        bt.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        bt.setFont(Font.font("Helvetica", FontWeight.LIGHT, BUTTON_FONT_SIZE));
        bt.setCursor(Cursor.HAND);

        return bt;
    }

}
