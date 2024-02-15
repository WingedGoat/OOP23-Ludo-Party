package start;

import javafx.application.Application;
import javafx.stage.Stage;
import view.utils.ViewUtility;

/**
 * Ludo Party App.
 */
public final class LudoPartyApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        ViewUtility.createStage();
    }

}
