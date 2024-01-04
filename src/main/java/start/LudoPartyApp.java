package start;

import javafx.application.Application;
import javafx.stage.Stage;
import view.FirstStage;

/**
 * 
 */
public final class LudoPartyApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        new FirstStage();
    }

}
