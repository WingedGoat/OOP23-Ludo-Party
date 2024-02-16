package view;

import controller.api.Controller;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.util.Collection;

import model.api.Item;

/** */
public class ShopPane extends BottomPane {


    /** 
     * The contructor for the ShopPane.
     * 
     * @param ctrl
     * 
    */
    public ShopPane(final Controller ctrl) {
        super();

        final Collection<Item> itemsColl = ctrl.getGame().getShowcase().values();
        final var items = itemsColl.stream().toList();

        for (int i = 0; i < itemsColl.size(); i++) {

            final Item item = items.get(i);
            final Button button = getButtons().get(i);

            buttonSetting(button, item, ctrl);
        } 
    }

    /**
     * Setting the button to be used in the shop.
     * 
     * @param button
     * @param item
     * @param ctrl
     */
    final void buttonSetting(final Button button, final Item item, final Controller ctrl) {

        button.setText(item.getName() + ". \nCosto: " + item.getPrice() + " ludollari.");
        button.setTooltip(new Tooltip(item.getDescription() + "\n" + item.getType().name()));
        replaceItemButtonsMap(button, item);

        button.setOnMouseEntered(cursorHand -> {
                button.setCursor(Cursor.HAND);
        });

        button.setOnMousePressed(e -> {

            final Button buttonpressed = (Button) e.getSource();
            final Item oldItem = getButtonMap().get(buttonpressed);
            final Item newItem = ctrl.getNewShopItem();

            if (ctrl.humanClickShopButton(buttonpressed, oldItem)) {
                getAlert().setContentText(ctrl.getShopMessage());
                buttonPressed(buttonpressed);
                buttonSetting(buttonpressed, newItem, ctrl);
            } else {
                getAlert().setContentText(ctrl.getShopMessage());
            }
        });
    }
}
