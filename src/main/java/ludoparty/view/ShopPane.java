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

import java.util.Collection;

import ludoparty.model.api.Item;
import ludoparty.controller.api.Controller;

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
        final Label shopLabel = new Label("Shop");
        shopLabel.setFont(Font.font("Helvetica", FontWeight.LIGHT, FONT_SIZE));

        this.setTop(shopLabel);

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
        replaceItemButtonsMap(button, item);

        button.setOnMouseEntered(cursorHand -> {
            button.setCursor(Cursor.HAND);
            button.setTooltip(new Tooltip(item.getDescription() + "\n" + item.getType().name()));
            board.getContainer().requestFocus();
        });

        button.setOnMousePressed(e -> {

            if (ctrl.isPossibleSelling()) {
                final Button buttonpressed = (Button) e.getSource();
                final Item itemChoose = getItemButtonMap().get(buttonpressed);

                sellingItem(buttonpressed, itemChoose, ctrl, board);
            }
            board.getContainer().requestFocus();
        });
    }

    /**
     * Let start the selling item action for extended use.
     * 
     * @param buttonpressed
     * @param itemChoose
     * @param ctrl
     * @param board
     */
    public void sellingItem(final Button buttonpressed, final Item itemChoose, final Controller ctrl,
            final BoardScene board) {

        ctrl.sellingItemToPlayer(itemChoose);
        final Label message = new Label(ctrl.getShopMessage());
        if (ctrl.isItemSelled()) {
            message.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            final Item newItem = ctrl.getNewShopItem();
            setButtonPressed(buttonpressed);
            buttonSetting(buttonpressed, newItem, ctrl, board);
        } else {
            message.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        getPopupMessage(message).show(board.getWindow()); 
    }

    /**
     * To able all the buttons of the shop when a pawn arrive on a the cell.
     */
    public void enableShop() {
        for (int i = 0; i < this.getButtons().size(); i++) {
            final Button button = getButtons().get(i);
            button.setDisable(false);
        }
    }

    /**
     * To disable all the shop buttons at the end of the turn that a pawn arrive on
     * a shop cell.
     */
    public void disableShop() {
        for (int i = 0; i < this.getButtons().size(); i++) {
            final Button button = getButtons().get(i);
            button.setDisable(true);
        }
    }
}
