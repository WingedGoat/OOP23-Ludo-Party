package ludoparty.view;

import ludoparty.controller.api.Controller;
import ludoparty.utils.Index;
import ludoparty.view.utils.ViewUtility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Save score stage.
 */
public class SaveScoreStage extends Stage {

    private static final int STAGE_WIDTH = 300;
    private static final int COL_GAP = 5;
    @SuppressWarnings("PMD")
    private TextField nameField;
    private Label nameLabel = new Label();

    /**
     * Contructor.
     * @param ctrl the controller
     */
    public SaveScoreStage(final Controller ctrl) {

        this.setTitle("Salvataggio");
        this.setMinWidth(STAGE_WIDTH);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(ctrl.getView().getWindow());

        final String playerName = ctrl.getGame().getTurn().getCurrentPlayer().getName();
        final int playerCoins = ctrl.getGame().getTurn().getCurrentPlayer().getCoins();
        final Label scoreLabel = new Label("Punteggio: " + playerCoins);
        if ("".equals(playerName)) {
            this.nameLabel = new Label("Inserisci il tuo nome:");
            this.nameField = new TextField();
        } else {
            nameLabel = new Label(playerName);
        }

        final Button saveBt = new Button("Salva");
        saveBt.setOnAction(e -> {
            if (nameField == null) {
                ctrl.saveScore(playerName);
            } else if (!this.nameField.getText().isEmpty()) {
                ctrl.saveScore(this.nameField.getText());
            }
            close();
            Runtime.getRuntime().exit(0);
        });

        this.setOnCloseRequest(e -> {
            this.close();
            Runtime.getRuntime().exit(0);
        });

        final GridPane pane = new GridPane();
        pane.setMinWidth(STAGE_WIDTH);
        pane.setPadding(new Insets(ViewUtility.INSET_OS));
        pane.setVgap(COL_GAP); 
        pane.setHgap(COL_GAP);
        pane.setAlignment(Pos.CENTER);

        pane.addRow(Index.ZERO, scoreLabel);
        pane.addRow(Index.THREE, this.nameLabel);
        if (this.nameField != null) {
            pane.addRow(Index.FIVE, this.nameField);
        }
        pane.addRow(Index.SEVEN, saveBt);

        final Scene scene = new Scene(pane);
        this.setScene(scene);

        this.setResizable(false);
        this.sizeToScene();

        this.show();
        this.setX(ctrl.getView().getX() + ((ctrl.getView().getWidth() - this.getWidth()) / Index.TWO));
        this.setY(ctrl.getView().getY() + ((ctrl.getView().getHeight() - this.getHeight()) / Index.TWO));
    }

}
