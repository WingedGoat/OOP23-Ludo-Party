package view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 
 */
public class PlayerPanel extends VBox {

    /**
     * Constructor.
     * @param b button to roll the Dice.
     */
    public PlayerPanel(final Button b) {
        super(b);
    }

    /**
     * Return the button pressed for rolling the Dice.
     * @return the button used for Dice rolling.
     */
    public Button getRollDiceButton() {
        return (Button) this.getChildren().get(0);
    }
}
