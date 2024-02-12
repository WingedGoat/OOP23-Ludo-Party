package view;

//import controller.api.Controller;
//import javafx.scene.control.Tooltip;
/** */
public class InventoryPane extends BottomPane {
    /**
     * The contructor for the InventoryPane.
     * 
     */
    public InventoryPane(/*final Controller controller*/) {
        super();
        getLeftButton().setText("*");
        getRightButton().setText("*");
        getCenterButton().setText("*");
        //itemA.setTooltip(new Tooltip(controller.getGame().getHumanPlayer().getPlayerInventory().getItemA().getDescription()));
        //itemB.setTooltip(new Tooltip(controller.getGame().getHumanPlayer().getPlayerInventory().getItemB().getDescription()));
        //itemC.setTooltip(new Tooltip(controller.getGame().getHumanPlayer().getPlayerInventory().getItemC().getDescription()));
    }
}
