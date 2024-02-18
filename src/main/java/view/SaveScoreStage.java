package view;

import controller.api.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Save score stage.
 */
public class SaveScoreStage extends Stage {

    /**
     * Contructor.
     * @param ctrl the controller
     */
    public SaveScoreStage(final Controller ctrl) {

        this.setTitle("Salvataggio");
        this.initModality(Modality.WINDOW_MODAL);
        this.setScene(ctrl.getView());

        final int playerCoins = ctrl.getGame().getHumanPlayer().getCoins();
        final Label scoreLabel = new Label("Punteggio: " + playerCoins);
        Label nameLabel = null;
        if ("".equals(ctrl.getGame().getHumanPlayer().getName())) {
            nameLabel = new Label("Inserisci il tuo nome:");
        } else {
            new Label(ctrl.getGame().getHumanPlayer().getName());
        }
        final TextField nameField = new TextField();

        final Button save = new Button("Salva");
        save.setOnAction(e -> {
            if (!nameField.getText().isEmpty()) {
                close();
                ctrl.saveScore(nameField.getText());
            } else {
                final Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Attenzione");
                alert.setHeaderText(null);
                alert.setContentText("Inserire un nome valido.");
                alert.show();
                alert.setX(getX() + (getWidth() - alert.getWidth()) / 2);
                alert.setY(getY() + (getHeight() - alert.getHeight()) / 2);
            }
        });

        final VBox vBox = new VBox();
        vBox.getChildren().addAll(scoreLabel, nameLabel, nameField);
        vBox.setAlignment(Pos.TOP_CENTER);

        final HBox hBox = new HBox();
        hBox.getChildren().addAll(save);

        final GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));

        pane.add(vBox, 0, 0);
        pane.add(hBox, 0, 1);

        final Scene scene = new Scene(pane);
        this.setScene(scene);

        this.setResizable(false);
        this.sizeToScene();

        this.show();
        this.setX(ctrl.getView().getX() + ((ctrl.getView().getWidth() - this.getWidth()) / 2));
        this.setY(ctrl.getView().getY() + ((ctrl.getView().getHeight() - this.getHeight()) / 2));
    }

}
