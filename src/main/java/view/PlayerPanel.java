package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * 
 */
public class PlayerPanel extends VBox {

    /**
     * Constructor.
     * @param button button to roll the Dice.
     * @param label label to display the Dice result.
     */
    public PlayerPanel(final Button button, final Label label) {
        super(button, label);
    }

    /**
     * Return the button pressed for rolling the Dice.
     * @return the button used for Dice rolling.
     */
    public Button getRollDiceButton() {
        return (Button) this.getChildren().get(0);
    }

    /**
     * Return the label showing the Dice result.
     * @return the label with Dice result.
     */
    public Label getDiceLabel() {
        return (Label) this.getChildren().get(1);
    }
}
