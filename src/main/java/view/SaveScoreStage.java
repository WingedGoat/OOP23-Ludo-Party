package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import controller.api.Controller;
import utils.Index;
import view.utils.ViewUtility;

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
        this.setScene(ctrl.getView());  //FIXME

        final int playerCoins = ctrl.getGame().getHumanPlayer().getCoins();
        final Label scoreLabel = new Label("Punteggio: " + playerCoins);
        if ("".equals(ctrl.getGame().getHumanPlayer().getName())) {
            this.nameLabel = new Label("Inserisci il tuo nome:");
            this.nameField = new TextField();
        } else {
            nameLabel = new Label(ctrl.getGame().getHumanPlayer().getName());
        }

        final Button saveBt = new Button("Salva");
        saveBt.setOnAction(e -> {
            if (nameField == null) {
                close();
                ctrl.saveScore(ctrl.getGame().getHumanPlayer().getName());
            } else if (!this.nameField.getText().isEmpty()) {
                close();
                ctrl.saveScore(this.nameField.getText());
            }
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
