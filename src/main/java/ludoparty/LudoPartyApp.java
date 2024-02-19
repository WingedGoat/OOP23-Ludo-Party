package ludoparty;

import ludoparty.view.utils.ViewUtility;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Ludo Party App.
 */
public final class LudoPartyApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        ViewUtility.createStage();
    }

}
