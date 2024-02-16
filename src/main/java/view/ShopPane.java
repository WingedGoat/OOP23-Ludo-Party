package view;

import controller.api.Controller;
//import javafx.application.Platform;
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
     * @param plinp
     * 
    */
    public ShopPane(final Controller ctrl, final InventoryPane plinp) {
        super();

        final Collection<Item> itemsColl = ctrl.getGame().getShowcase().values();
        final var items = itemsColl.stream().toList();

        for (int i = 0; i < itemsColl.size(); i++) {

            final Item item = items.get(i);
            final Button button = getButtons().get(i);

            button.setText(item.getName() + ". \nCosto: " + item.getPrice() + " ludollari.");
            button.setTooltip(new Tooltip(item.getDescription() + "\n" + item.getType().name()));
            this.getButtonMap().put(button, item);

            button.setOnMouseEntered(cursorHand -> {
                button.setCursor(Cursor.HAND);
            });

            button.setOnMousePressed(e -> {
                if (ctrl.humanClickShopButton(button, item)) {
                    plinp.addItem(item, ctrl);
                    getAlert().setContentText(ctrl.getShopMessage());
                    buttonPressed(button);
                } else {
                    getAlert().setContentText(ctrl.getShopMessage());
                }
            });

        } 
    }
}
