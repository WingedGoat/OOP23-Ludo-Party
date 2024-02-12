package view;

import controller.api.Controller;
import javafx.scene.control.Tooltip;
/** */
public class ShopPane extends BottomPane {
    /** 
     * The contructor for the ShopPane.
     * 
     * @param controller
    */
    public ShopPane(final Controller controller) {
        super();

        getLeftButton().setText(controller.getPlayersNumber() + ". \n Costo: " + " ludollari.");
        getLeftButton().setTooltip(new Tooltip(" \n"));
        getCenterButton().setText(". \n Costo: " + " ludollari.");
        getCenterButton().setTooltip(new Tooltip(" \n"));
        getRightButton().setText(". \n Costo: " + " ludollari.");
        getRightButton().setTooltip(new Tooltip(" \n"));
    }
}
