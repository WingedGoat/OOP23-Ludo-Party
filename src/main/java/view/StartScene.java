package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import utility.Constants;

/**
 * Start Scene represents the first Scene 
 * contained by the Stage.
 */
public class StartScene extends Scene {

    /**
     * Columns gap.
     */
    public static final int COL_GAP = 5;
    /**
     * Font size.
     */
    public static final int FONT_SIZE = 28;
    /** 
     * Index 0.
     */
    public static final int INDEX_0 = 0;
    /**
     * Index 1.
     */
    public static final int INDEX_1 = 1;
    /**
     * Index 4.
     */
    public static final int INDEX_4 = 4;
    /**
     * Index 5.
     */
    public static final int INDEX_5 = 5;
    /**
     * Index 7.
     */
    public static final int INDEX_7 = 7;
    /**
     * Title X position.
     */
    public static final int X_POS = 7;
    /**
     * Title Y position.
     */
    public static final int Y_POS = 35;


    /**
     * Constructs the Start Scene.
     * @param stage
     *              the stage
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

        final Text title = createText("Ludo Party . . ");
        final Text text = new Text("Inserisci il tuo nome:");
        final TextField textField = new TextField();
        final Button button = createButton(stage);

        gridPane.add(title, INDEX_0, INDEX_0);
        gridPane.add(text, INDEX_0, INDEX_4); 
        gridPane.add(textField, INDEX_0, INDEX_5); 
        gridPane.add(button, INDEX_1, INDEX_7); 

        this.setFill(Color.valueOf("0077b6"));
    }

    private Text createText(final String txt) {
        final Text text = new Text();
        text.setText(txt);
        text.setFill(Color.valueOf("03045b"));
        text.setFont(Font.font("null", FontWeight.BOLD, FONT_SIZE));
        text.setX(X_POS);
        text.setY(Y_POS);

        return text;
    }

    private Button createButton(final Stage stage) {
        final Button button = new Button("next >");

        button.setOnAction((event) -> {
            final ObservableList<Node> nodes = ((GridPane) this.getRoot()).getChildrenUnmodifiable();
            final String playerName = ((TextField) nodes.get(2)).getText();

            //System.out.println("Button clicked! " + playerName);
            stage.setScene(ViewUtility.createChoosePlayerScene(stage, playerName));
        });
        button.setCursor(Cursor.HAND);

        return button;
    }

}
