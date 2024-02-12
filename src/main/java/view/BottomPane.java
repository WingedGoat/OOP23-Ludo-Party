package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import utility.Constants;
/** 
 * 
*/
public class BottomPane extends BorderPane {

    private final Button leftButton = new Button();
    private final Button centerButton = new Button();
    private final Button rightButton = new Button();
    /** 
     * The contructor for the BottomPane.
    */
    BottomPane() {
        leftButton.setPrefSize(Constants.BUTTON_WIDHT, Constants.BUTTON_HEIGHT);
        centerButton.setPrefSize(Constants.BUTTON_WIDHT, Constants.BUTTON_HEIGHT);
        rightButton.setPrefSize(Constants.BUTTON_WIDHT, Constants.BUTTON_HEIGHT);
        this.setLeft(leftButton);
        this.setCenter(centerButton);
        this.setRight(rightButton);
    }
    /** 
     * @return the left button
    */
    public final Button getLeftButton() {
        return copyButton(this.leftButton);
    }
    /** 
     * @return the middle button
    */
    public final Button getCenterButton() {
        return copyButton(this.centerButton);
    }
    /** 
     * @return the right button
    */
    public final Button getRightButton() {
        return copyButton(this.rightButton);
    }

    /**
     * Return a copy of the button.
     * 
     * @param button
     * 
     * @return a copy
     */
    public final Button copyButton(final Button button) {
        return button;
    }

}
