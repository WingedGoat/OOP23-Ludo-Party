package ludoparty.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ludoparty.utils.BColor;
import ludoparty.utils.Index;
import ludoparty.view.utils.ResourcePath;
import ludoparty.view.utils.ViewUtility;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private static final int TEXT_FIELD_WIDTH = 250;
    private static final int TEXT_FIELD_MAX_LENGTH = 15;
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
        final PlayerNameTextField textField = new PlayerNameTextField(font);

        final Button button = createGoButton(stage);

        gridPane.addRow(Index.ZERO, title);
        gridPane.addRow(Index.FIFTEEN, text);
        gridPane.addRow(Index.EIGHTEEN, textField);
        gridPane.addRow(Index.FORTY, button);

        addBackgroundImage(gridPane);

        stage.setOnCloseRequest(e -> {
            stage.close();
        });
    }

    private Text createTitleText(final String txt) {
        final Text text = new Text();
        text.setText(txt);
        text.setFill(BColor.GREY.get());
        text.setX(TITLE_X_POS);
        text.setY(TITLE_Y_POS);

        final InputStream in = ClassLoader.getSystemResourceAsStream(ResourcePath.TITLE_FONT.getPath());
        if (in != null) {
            final Font font = Font.loadFont(in, TITLE_FONT_SIZE);
            text.setFont(font);

            try {
                in.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

        return text;
    }

    private Text createText(final String txt, final Font font) {
        final Text text = new Text(txt);
        text.setFill(BColor.GREY.get());
        text.setFont(font);

        return text;
    }

    private Button createGoButton(final Stage stage) {
        final Button button = new Button("GO");
        button.setContentDisplay(ContentDisplay.RIGHT);
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setTranslateX(X_TRANSLATE);
        button.setTranslateY(Y_TRANSLATE);
        button.setFont(Font.font("Helvetica", FontWeight.LIGHT, ViewUtility.BUTTON_FONT_SIZE));

        final URL imgURL = ClassLoader.getSystemResource(ResourcePath.ARROW_ICON.getPath());
        if (imgURL != null) {
            final ImageView arrowRIght = new ImageView(new Image(imgURL.toString()));
            arrowRIght.setFitHeight(ViewUtility.BUTTON_FONT_SIZE);
            arrowRIght.setPreserveRatio(true);
            button.setGraphic(arrowRIght);
        }

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
            final URL imgURL = ClassLoader.getSystemResource(ResourcePath.BACKGROUND_IMG.getPath());
            if (imgURL != null) {
                final Image img = new Image(imgURL.toString()); 
                final BackgroundImage bgImg = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                gridPane.setBackground(new Background(bgImg));
            } else {
                LOGGER.error("imgURL is null; BACKGROUND_IMG Path: " + ResourcePath.BACKGROUND_IMG.getPath());

            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Not possible to add background image");
        }
    }


    private static class PlayerNameTextField extends TextField {

        PlayerNameTextField(final Font font) {
            this.setMinHeight(TEXT_FIELD_HEIGHT);
            this.setMaxWidth(TEXT_FIELD_WIDTH);
            this.setFont(font);
            this.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(
                    final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                    if (getText().length() > TEXT_FIELD_MAX_LENGTH) {
                        final String s = getText().substring(Index.ZERO, TEXT_FIELD_MAX_LENGTH);
                        setText(s);
                    }
                }
            });
        }
    }

}
