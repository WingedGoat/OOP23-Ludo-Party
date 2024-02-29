package ludoparty.view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ludoparty.controller.api.Controller;
import ludoparty.model.api.Item;
import ludoparty.utils.Index;

/**
 * The Inventory Pane at the bottom of the board.
 */
public class InventoryPane extends BottomPane {

    private boolean empyLeft, empyCenter, empyRight;

    /**
     * The contructor for the InventoryPane.
     */
    public InventoryPane() {
        super();

        final Label shopLabel = new Label("Inventory");
        shopLabel.setFont(Font.font("Helvetica", FontWeight.LIGHT, FONT_SIZE));

        this.setTop(shopLabel);
        empyLeft = true;
        empyCenter = true;
        empyRight = true;
        for (int i = 0; i < this.getButtons().size(); i++) {

            final Button button = getButtons().get(i);

            button.setText("*");
            button.setDisable(true);
            replaceItemButtonsMap(button, null);
        }
    }

    /**
     * Adding an item in the first empy button starting from the left to the right.
     * 
     * @param item the item to add in the inventory
     * @param ctrl the controller
     * @param board the board for the view interaction
     */
    public void addItem(final Item item, final Controller ctrl, final BoardScene board) { 

        if (isEmpyCenter()) { 
            if (isEmpyLeft()) { 
                setEmpyLeft(false);
                setItemButton(getLeftButton(), item, ctrl, board);
            } else {
                setEmpyCenter(false);
                setItemButton(getCenterButton(), item, ctrl, board);
            }
        } else {
            setEmpyRight(false);
            setItemButton(getRightButton(), item, ctrl, board);
        }
    }

    /**
     * 
     * @param button
     * @param item
     * @param ctrl
     * @param board
     */
    void setItemButton(final Button button, final Item item, final Controller ctrl, final BoardScene board) {

        button.setText(item.getName());
        button.setDisable(false);
        replaceItemButtonsMap(button, item);

        button.setOnMouseEntered(mouseEvent -> {
            button.setCursor(Cursor.HAND);
            button.setTooltip(new Tooltip(item.getDescription() + "\n" + item.getType()));
            board.getContainer().requestFocus();
        });

        button.setOnMousePressed(mouseEvent -> {

            final Button pressdButton = (Button) mouseEvent.getSource();
            final Item chooseItem = getItemButtonMap().get(pressdButton);

            if (ctrl.getGame().getTurn().getCurrentPlayer().isDiceRolled() && chooseItem.getType().equals(Item.ItemType.BONUS)) {
                final Label message = new Label("NON PUOI USARE L'ITEM IN QUESTO MOMENTO!");
                message.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                getPopupMessage(message).show(board.getWindow());
            } else {
                setButtonPressed(pressdButton);
                ctrl.setItemToUse(chooseItem);

                if (ctrl.clickBonusButton(chooseItem)) {
                    ctrl.getGame().getHumanPlayer().useItem(chooseItem, ctrl.getGame().getHumanPlayer(), 
                            ctrl.getGame().getHumanPlayer().getPawns().get(Index.ONE), ctrl.getGame());
                    final Label message = new Label("Hai usato " + chooseItem.getName() + ".");
                    message.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                    getPopupMessage(message).show(board.getWindow());
                } 
                pressdButton.setDisable(true);
                ordinateUsedInventory(pressdButton, ctrl, board);
            }

            board.getContainer().requestFocus();
        });
    }

    /**
     * 
     * @param button
     */
    public void setEmpyButton(final Button button) {
        button.setText("*");
        button.setDisable(true);
        replaceItemButtonsMap(button, null);
    }
    /**
     * Reordinate the inventory view on base of the button used.
     * 
     * @param buttonUsed
     * @param ctrl
     * @param board
     */
    public void ordinateUsedInventory(final Button buttonUsed, final Controller ctrl, final BoardScene board) {
        final Button  right = getRightButton(), center = getCenterButton();

        if (isEmpyCenter() && isEmpyRight()) {
            setEmpyButton(getLeftButton());
            setEmpyLeft(true);
        } else if (isEmpyRight()) {
            if (getLeftButton().equals(buttonUsed)) {
                setItemButton(getLeftButton(), getItemButtonMap().get(center), ctrl, board);
            } 
            setEmpyButton(getCenterButton());
            setEmpyCenter(true);
        } else { 
            if (getLeftButton().equals(buttonUsed)) {
                setItemButton(getLeftButton(), getItemButtonMap().get(center), ctrl, board);
                setItemButton(getCenterButton(), getItemButtonMap().get(right), ctrl, board);
            }
            if (center.equals(buttonUsed)) {
                setItemButton(getCenterButton(), getItemButtonMap().get(right), ctrl, board);
            } 
            setEmpyButton(getRightButton());
            setEmpyRight(true);
        }
    }

    /**
     * Set the new value if the left button is empy or not.
     * 
     * @param newEmpyLeft
     */
    public void setEmpyLeft(final boolean newEmpyLeft) {
        this.empyLeft = newEmpyLeft;
    }

    /**
     * Set the new value if the center button is empy or not.
     * 
     * @param newEmpyCenter
     */
    public void setEmpyCenter(final boolean newEmpyCenter) {
        this.empyCenter = newEmpyCenter;
    }

    /**
     * Set the new value if the right button is empy or not.
     * 
     * @param newEmpyRight
     */
    public void setEmpyRight(final boolean newEmpyRight) {
        this.empyRight = newEmpyRight;
    }

    /**
     * Return true if the left button is empy,
     * false instead.
     * 
     * @return true or false
     */
    public boolean isEmpyLeft() {
        return empyLeft;
    }

    /**
     * Return true if the central button is empy,
     * false instead.
     * 
     * @return true or false
     */
    public boolean isEmpyCenter() {
        return empyCenter;
    }

    /**
     * Return true if the right button is empy,
     * false instead.
     * 
     * @return true or false
     */
    public boolean isEmpyRight() {
        return empyRight;
    }
}
