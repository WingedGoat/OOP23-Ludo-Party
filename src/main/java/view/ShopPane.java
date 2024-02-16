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

import java.util.Collection;

import model.api.Item;
import controller.api.Controller;

/**
 * The Shop Pane at the bottom of the board.
 */
public class ShopPane extends BottomPane {

    /**
     * The contructor for the ShopPane.
     * 
     * @param ctrl  the controller
     * @param board the board
     */
    public ShopPane(final Controller ctrl, final BoardScene board) {
        super();

        final Collection<Item> itemsColl = ctrl.getGame().getShowcase().values();
        final var items = itemsColl.stream().toList();

        for (int i = 0; i < itemsColl.size(); i++) {
            final Item item = items.get(i);
            final Button button = getButtons().get(i);

            buttonSetting(button, item, ctrl, board);
        }
    }

    /**
     * Setting the button to be used in the shop.
     * 
     * @param button
     * @param item
     * @param ctrl
     * @param board
     */
    final void buttonSetting(final Button button, final Item item, final Controller ctrl, final BoardScene board) {

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
            final boolean possibleSelling = ctrl.humanClickShopButton(buttonpressed, oldItem);

            if (possibleSelling) {

                final Label message = new Label(ctrl.getShopMessage());
                message.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

                getPopupMessage().getContent().add(message);
                getPopupMessage().show(board.getWindow());

                buttonPressed(buttonpressed);
                buttonSetting(buttonpressed, newItem, ctrl, board);
            } else {

                final Label message = new Label(ctrl.getShopMessage());
                message.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

                getPopupMessage().getContent().add(message);
                getPopupMessage().show(board.getWindow());
            }
        });
    }
}
