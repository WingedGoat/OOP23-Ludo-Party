package view;

import controller.api.Controller;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import java.util.Collection;

import javafx.scene.paint.Color;

import model.api.Item;

/** */
public class ShopPane extends BottomPane {


    /** 
     * The contructor for the ShopPane.
     * 
     * @param ctrl
     * 
     * @param board
     * 
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

     /**
     * To able all the buttons of the shop when a pawn arrive on a the cell.
     */
    public void ableShop() {
        for (int i = 0; i < this.getButtons().size(); i++) {
            final Button button = getButtons().get(i);
            button.setDisable(false);
        }
    }

    /**
     * To disable all the shop buttons at the end of the turn that a pawn arrive on a shop cell.
     */
    public void disableShop() {
        for (int i = 0; i < this.getButtons().size(); i++) {
            final Button button = getButtons().get(i);
            button.setDisable(true);
        }
    }
}
