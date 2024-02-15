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

            createShopButton(item, button, ctrl, plinp);

        } 
    }

    /**
     * Create all the caracteristic for the button.
     * 
     * @param item
     * @param button
     * @param ctrl
     * @param plinp
     */
    void createShopButton(final Item item, final Button button, 
    final Controller ctrl, final InventoryPane plinp) {
        button.setText(item.getName() + ". \nCosto: " + item.getPrice() + " ludollari.");
        button.setTooltip(new Tooltip(item.getDescription() + "\n" + item.getType().name()));
        this.getButtonMap().put(button, item);
        button.setOnMouseEntered(cursorHand -> {
            button.setCursor(Cursor.HAND);
        });
        setMousePressed(item, button, ctrl, plinp);
    }

    /**
     * Set the MouseEvent for the button.
     * 
     * @param item
     * @param button
     * @param ctrl
     * @param plinp
     */
    void setMousePressed(final Item item, final Button button, 
            final Controller ctrl, final InventoryPane plinp) {
        button.setOnMousePressed(e -> {
            if (ctrl.humanClickShopButton(button, item)) {
                plinp.addItem(item, ctrl);
                getAlert().setContentText(ctrl.getShopMessage());
                createShopButton(ctrl.getNewShopItem(), button, ctrl, plinp);
            } else {
                getAlert().setContentText(ctrl.getShopMessage());
            }
        }); 
    }

}
