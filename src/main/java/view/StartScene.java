package view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import utils.BColor;
import utils.Index;
import view.utils.ResourcePath;
import view.utils.ViewUtility;

/**
 * Start Scene represents the first Scene 
 * contained by the Stage.
 */
public class StartScene extends Scene {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static final int TITLE_FONT_SIZE = 48;
    private static final int TITLE_X_POS = 7;
    private static final int TITLE_Y_POS = 35;
    private static final int TEXT_FONT_SIZE = 18;
    private static final int TEXT_FIELD_HEIGHT = 20;
    // button props
    private static final int BUTTON_WIDTH = 90;
    private static final int BUTTON_HEIGHT = 40;
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
        gridPane.setMinSize(ViewUtility.HOME_WINDOW_WIDTH, ViewUtility.HOME_WINDOW_HEIGHT);
        gridPane.setPadding(new Insets(ViewUtility.INSET_OS));
        gridPane.setVgap(COL_GAP); 
        gridPane.setHgap(COL_GAP);
        gridPane.setAlignment(Pos.CENTER); 

        final Text title = createTitleText("Ludo Party");

        final Font font = Font.font("Helvetica", FontWeight.LIGHT, TEXT_FONT_SIZE);
        final Text text = createText("Inserisci il tuo nome:", font);
        final TextField textField = createTextField(font);

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
        text.setFill(BColor.GREY.get());
        text.setX(TITLE_X_POS);
        text.setY(TITLE_Y_POS);

        try {
            final Font font = Font.loadFont(new FileInputStream(new File(ResourcePath.TITLE_FONT.getPath())), TITLE_FONT_SIZE);
            text.setFont(font);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }

        return text;
    }

    private Text createText(final String txt, final Font font) {
        final Text text = new Text(txt);
        text.setFill(BColor.GREY.get());
        text.setFont(font);

        return text;
    }

    private TextField createTextField(final Font font) {
        final TextField textField = new TextField();
        textField.setMinHeight(TEXT_FIELD_HEIGHT);
        textField.setFont(font);

        return textField;
    }

    private Button createButton(final Stage stage) {
        final Button button = new Button("GO");
        button.setContentDisplay(ContentDisplay.RIGHT);
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setTranslateX(X_TRANSLATE);
        button.setTranslateY(Y_TRANSLATE);
        button.setFont(Font.font("Helvetica", FontWeight.LIGHT, ViewUtility.BUTTON_FONT_SIZE));

        final File file = new File(ResourcePath.ARROW_ICON.getPath());
        final ImageView arrowRIght = new ImageView(new Image(file.toURI().toString()));
        arrowRIght.setFitHeight(ViewUtility.BUTTON_FONT_SIZE);
        arrowRIght.setPreserveRatio(true);
        button.setGraphic(arrowRIght);

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
            final Image img = new Image(new File(ResourcePath.BACKGROUND_IMG.getPath()).toURI().toString()); 
            final BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            gridPane.setBackground(new Background(bgImg));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Background Image Not Found at path: " + ResourcePath.BACKGROUND_IMG.getPath());
        }
    }

}
