package view;

import controller.api.Controller;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import model.api.Item;

//import model.api.Item;
//import utility.Index;

//import controller.api.Controller;
//import javafx.scene.control.Tooltip;
/** */
public class InventoryPane extends BottomPane {

    /**
     * The contructor for the InventoryPane.
     */
    public InventoryPane() {
        super();

        for (int i = 0; i < this.getButtons().size(); i++) {
            final Button button = getButtons().get(i);
            button.setText("*");
        }

    }

    /**
     * Adding an item in the first empy button starting from the left to the right.
     * 
     * @param item added in the inventory
     * @param ctrl the controller to call
     */
    public void addItem(final Item item, final Controller ctrl) { 

        final Button newButton = new Button();

        newButton.setText(item.getName());
        newButton.setTooltip(new Tooltip(item.getDescription() + item.getType()));
        newButton.setDisable(false);

        newButton.setOnMouseEntered(mouseEvent -> {
            newButton.setCursor(Cursor.HAND);
        });

        newButton.setOnMousePressed(mouseEvent -> {
            buttonPressed(newButton);
            if (ctrl.clickBonusButton(getButtonMap().get(newButton))) {
                ctrl.getGame().getHumanPlayer().useItem(item, null, null, ctrl.getGame());
                getAlert().setContentText("Hai usato " + item.getName() + ".");
                ordinateUsedInventory(newButton);
            } 
            ctrl.setItemToUse(item);
            ordinateUsedInventory(newButton);
        });

        if (getCenterButton().equals(this.getEmpyButton())) { 
            if (getLeftButton().equals(this.getEmpyButton())) { 
                setLeftButton(newButton);
                replaceItemButtonsMap(getLeftButton(), item);
            } else {
                setCenterButton(newButton);
                replaceItemButtonsMap(getCenterButton(), item);
            }
        } else {
            setRightButton(newButton);
            replaceItemButtonsMap(getRightButton(), item);
        }
    }

    /**
     * Reordinate the inventory view on base of the button used.
     * 
     * @param buttonUsed
     */
    public void ordinateUsedInventory(final Button buttonUsed) {
        final Button center = getCenterButton(), right = getRightButton();
        setRightButton(this.getEmpyButton());
        replaceItemButtonsMap(getRightButton(), null);
        if (!buttonUsed.equals(right)) {
            setCenterButton(right);
            replaceItemButtonsMap(getCenterButton(), getButtonMap().get(right));
            if (!buttonUsed.equals(center)) {
                setLeftButton(center);
                replaceItemButtonsMap(getLeftButton(), getButtonMap().get(center));
            }
        } 
    }
}
