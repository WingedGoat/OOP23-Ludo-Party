package view;

import javafx.stage.Stage;
/**
 * First Stage showed when application is started.
 */
public class FirstStage extends Stage {

    /**
     * Constructs the First Stage.
     */
    public FirstStage() {
        this.setTitle("Ludo Party");
        this.setResizable(false);
        this.setScene(new FirstScene(this));
        this.show();
    }

}
