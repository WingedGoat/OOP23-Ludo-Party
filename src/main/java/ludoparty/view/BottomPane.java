package ludoparty.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ludoparty.model.api.Item;

/** 
 * 
*/
public class BottomPane extends BorderPane {

    private static final int ITEM_BUTTON_WIDHT = 250;
    private static final int ITEM_BUTTON_HEIGTH = 60;
    /**
     * The font size.
     */
    protected static final int FONT_SIZE = 16; 
    private static final int X_POS_POPUP = 632;
    private static final int Y_POS_POPUP = 742;
    private Button leftButton = new Button();
    private Button centerButton = new Button();
    private Button rightButton = new Button();
    private final List<Button> buttons = new ArrayList<>();
    private final Map<Button, Item> buttonsMap = new HashMap<>();
    private Button buttonPressed;

    /** 
     * The contructor for the BottomPane.
    */
    public BottomPane() {

        leftButton.setPrefSize(ITEM_BUTTON_WIDHT, ITEM_BUTTON_HEIGTH);
        centerButton.setPrefSize(ITEM_BUTTON_WIDHT, ITEM_BUTTON_HEIGTH);
        rightButton.setPrefSize(ITEM_BUTTON_WIDHT, ITEM_BUTTON_HEIGTH);

        this.setLeft(leftButton);
        this.setCenter(centerButton);
        this.setRight(rightButton);

        buttons.add(leftButton);
        buttons.add(centerButton);
        buttons.add(rightButton);

        buttonsMap.put(leftButton, null);
        buttonsMap.put(centerButton, null);
        buttonsMap.put(rightButton, null);

        this.buttonPressed = null;
    }
    /** 
     * @return a copy of the left button
    */
    public final Button getLeftButton() {
        return copyButton(this.leftButton);
    }
    /** 
     * @return a copy of the middle button
    */
    public final Button getCenterButton() {
        return copyButton(this.centerButton);
    }
    /** 
     * @return a copy of the right button
    */
    public final Button getRightButton() {
        return copyButton(this.rightButton);
    }

    /**
     * Modify the left button.
     * 
     * @param newButton
     */
    public void setLeftButton(final Button newButton) {
        this.leftButton = newButton;
    }
    /**
     * Modify the center button.
     * 
     * @param newButton
     */
    public void setCenterButton(final Button newButton) {
        this.centerButton = newButton;
    }

    /**
     * Modify the right button.
     * 
     * @param newButton
     */
    public void setRightButton(final Button newButton) {
        this.rightButton = newButton;
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

    /**
     * Return the buttons list.
     * 
     * @return the buttons list
     */
    public List<Button> getButtons() {
        final List<Button> copyButtons = new ArrayList<>();
        copyButtons.add(getLeftButton());
        copyButtons.add(getCenterButton());
        copyButtons.add(getRightButton());
        return copyButtons;
    }

    /**
     * Return a copy of the button map.
     * 
     * @return a copy
     */
    public final  Map<Button, Item> getItemButtonMap() {
        final Map<Button, Item> copybuttonsMap = new HashMap<>();
        copybuttonsMap.put(leftButton, buttonsMap.get(leftButton));
        copybuttonsMap.put(centerButton, buttonsMap.get(centerButton));
        copybuttonsMap.put(rightButton, buttonsMap.get(rightButton));
        return copybuttonsMap;
    }

    /**
     * Replace a new entry the button map.
     * 
     * @param button
     *          the button key
     * @param item
     *          the new item to replace
     */
    public void replaceItemButtonsMap(final Button button, final Item item) {
            this.buttonsMap.replace(button, item);
    }

    /**
     * Set the button as pressed.
     * 
     * @param button
     */
    public void setButtonPressed(final Button button) {
        this.buttonPressed = button;
    }

    /**
     * Return the last button pressed.
     * 
     * @return the last button pressed
     */
    public Button getButtonPressed() {
        return this.buttonPressed;
    }

    /**
     * Return a new popup used to advise the player.
     * 
     * @param messageText the popup message
     * 
     * @return the popup
     */
    public Popup getPopupMessage(final Label messageText) {
        final Popup popup = new Popup();
        popup.getContent().add(messageText);
        popup.setX(X_POS_POPUP);
        popup.setY(Y_POS_POPUP);

        popup.setAutoHide(true);
        return popup;
    }

}
