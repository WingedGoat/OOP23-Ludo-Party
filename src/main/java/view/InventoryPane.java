package view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import controller.api.Controller;
import model.api.Item;
import utils.Index;

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

        for (int i = 0; i < this.getButtons().size(); i++) {

            final Button button = getButtons().get(i);
            button.setDisable(true);

            if (button.equals(getLeftButton())) {
                empyLeft = true;
            } 
            if (button.equals(getCenterButton())) {
                empyCenter = true;
            } 
            if (button.equals(getRightButton())) {
                empyRight = true;
            }

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
            setEmpyLeft(false);
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
        button.setTooltip(new Tooltip(item.getDescription() + item.getType()));
        button.setDisable(false);
        replaceItemButtonsMap(button, item);

        button.setOnMouseEntered(mouseEvent -> {
            button.setCursor(Cursor.HAND);
            board.getBorderPane().requestFocus();
        });

        button.setOnMousePressed(mouseEvent -> {

            final Button pressdButton = (Button) mouseEvent.getSource();
            final Item chooseItem = getItemButtonMap().get(pressdButton);
            buttonPressed(pressdButton);

            if (ctrl.clickBonusButton(chooseItem)) {
                ctrl.getGame().getHumanPlayer().useItem(chooseItem, ctrl.getGame().getHumanPlayer(), 
                        ctrl.getGame().getHumanPlayer().getPawns().get(Index.ONE), ctrl.getGame());
                final Label message = new Label("Hai usato " + chooseItem.getName() + ".");
                getPopupMessage().getContent().add(message);
                message.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                getPopupMessage().show(board.getWindow());
            } 
            ctrl.setItemToUse(chooseItem);
            pressdButton.setDisable(true);
            ordinateUsedInventory(pressdButton, ctrl, board);
            board.getBorderPane().requestFocus();
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
        } else if (isEmpyRight()) {
            if (getLeftButton().equals(buttonUsed)) {
                setItemButton(getLeftButton(), getItemButtonMap().get(center), ctrl, board);
            } 
            setEmpyButton(getCenterButton());
        } else { 
            if (getLeftButton().equals(buttonUsed)) {
                setItemButton(getLeftButton(), getItemButtonMap().get(center), ctrl, board);
                setItemButton(getCenterButton(), getItemButtonMap().get(right), ctrl, board);
            }
            if (center.equals(buttonUsed)) {
                setItemButton(getCenterButton(), getItemButtonMap().get(right), ctrl, board);
            } 
            setEmpyButton(getRightButton());
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
