package start;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 */
@SuppressWarnings("all")
public final class LudoPartyApp extends Application {
    /**
     * Home window width.
     */
    public static final int HOME_WINDOW_WIDTH = 450;
    /**
     * Home window height.
     */
    public static final int HOME_WINDOW_HEIGHT = 600;
    /**
     * Inset offset.
     */
    public static final int INSET_OS = 10;
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

    @Override
    public void start(final Stage stage) throws Exception {
        initUI(stage);
    }

    /**
     * Initialize the User Interface.
     * @param stage
     *              the primary stage
     */
    private void initUI(final Stage stage) {

        final GridPane gridPane = new GridPane();
        gridPane.setMinSize(HOME_WINDOW_WIDTH, HOME_WINDOW_HEIGHT);
        gridPane.setPadding(new Insets(INSET_OS));
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(COL_GAP); 
        gridPane.setHgap(COL_GAP);
        //Setting the Grid alignment 
        gridPane.setAlignment(Pos.CENTER); 
 
        final Text text = new Text("Inserisci il tuo nome:");
        TextField textField = new TextField();
        //
        final Button button = new Button();
        button.setText("Start!");
        button.setOnAction((event) -> {
            System.out.println("Button clicked!");
        });
        button.setCursor(Cursor.HAND);

        final Text title = new Text();
        title.setText("Ludo Party . . ");
        title.setFill(Color.valueOf("03045b"));
        title.setFont(Font.font("null", FontWeight.BOLD, FONT_SIZE));
        title.setX(X_POS);
        title.setY(Y_POS);

        gridPane.add(title, INDEX_0, INDEX_0);
        gridPane.add(text, INDEX_0, INDEX_4); 
        gridPane.add(textField, INDEX_0, INDEX_5); 
        gridPane.add(button, INDEX_1, INDEX_7); 

        final Scene scene = new Scene(gridPane);
        scene.setFill(Color.valueOf("0077b6"));
        stage.setTitle("Ludo Party");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
