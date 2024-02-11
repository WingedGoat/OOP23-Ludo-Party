package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import utility.BColor;
import utility.Constants;
import utility.Index;

/**
 * Start Scene represents the first Scene 
 * contained by the Stage.
 */
public class StartScene extends Scene {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final String TITLE_FONT_PATH = Path.of("." + ViewUtility.FILE_SEPARATOR + "resources" 
        + ViewUtility.FILE_SEPARATOR + "fonts" + ViewUtility.FILE_SEPARATOR + "MajorMonoDisplay.ttf").toString();
    private static final int TITLE_FONT_SIZE = 48;
    private static final int TITLE_X_POS = 7;
    private static final int TITLE_Y_POS = 35;
    private static final int TEXT_FONT_SIZE = 16;
    // button props
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_FONT_SIZE = 14;
    private static final int X_TRANSLATE = 280;
    private static final int Y_TRANSLATE = 20;

    private static final int COL_GAP = 5;

    /**
     * Constructs the Start Scene.
     * 
     * @param stage the stage
     */
    public StartScene(final Stage stage) {
        super(new GridPane());
        stage.setTitle("Ludo");

        final GridPane gridPane = (GridPane) this.getRoot();
        gridPane.setMinSize(Constants.HOME_WINDOW_WIDTH, Constants.HOME_WINDOW_HEIGHT);
        gridPane.setPadding(new Insets(Constants.INSET_OS));
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(COL_GAP); 
        gridPane.setHgap(COL_GAP);
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 

        final Text title = createTitleText("Ludo Party");
        final Text text = new Text("Inserisci il tuo nome:");
        text.setFill(Color.valueOf(BColor.GREY.get()));
        text.setFont(Font.font("Helvetica", FontWeight.LIGHT, FontPosture.REGULAR, TEXT_FONT_SIZE));

        final TextField textField = new TextField();
        final Button button = createButton(stage);

        gridPane.addRow(Index.ZERO, title);
        gridPane.addRow(Index.FIFTEEN, text);
        gridPane.addRow(Index.EIGHTEEN, textField);
        gridPane.addRow(Index.FORTY, button);

        addBackgroundImage(gridPane);
    }

    private Text createTitleText(final String txt) {
        final Text text = new Text();
        text.setText(txt);
        text.setFill(Color.valueOf("#f1f1f1"));
        text.setX(TITLE_X_POS);
        text.setY(TITLE_Y_POS);

        try {
            final Font font = Font.loadFont(new FileInputStream(new File(TITLE_FONT_PATH)), TITLE_FONT_SIZE);
            text.setFont(font);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }

        return text;
    }

    private Button createButton(final Stage stage) {
        final Button button = new Button("next >");
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setTranslateX(X_TRANSLATE);
        button.setTranslateY(Y_TRANSLATE);
        button.setFont(Font.font("Helvetica", FontWeight.LIGHT, FontPosture.REGULAR, BUTTON_FONT_SIZE));

        button.setOnAction((event) -> {
            final ObservableList<Node> nodes = ((GridPane) this.getRoot()).getChildrenUnmodifiable();
            final String playerName = ((TextField) nodes.get(2)).getText();
            stage.setScene(ViewUtility.createChoosePlayerScene(stage, playerName));
        });
        button.setCursor(Cursor.HAND);

        return button;
    }

    private void addBackgroundImage(final GridPane gridPane) {
        try {
            final Image img = new Image(new File(ViewUtility.BACKGROUND_IMG_PATH).toURI().toString()); 
            final BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            gridPane.setBackground(new Background(bgImg));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Background Image Not Found at path: " + ViewUtility.BACKGROUND_IMG_PATH);
        }
    }

}
