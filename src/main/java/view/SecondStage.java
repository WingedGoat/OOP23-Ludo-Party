package view;

import javafx.stage.Stage;
/**
 * First Stage showed while the player have to choose 
 * the number of players in the game.
 */
public class SecondStage extends Stage {

    /**
     * Constructs the Second Stage.
     * @param playerName
     *                  the player name
     */
    public SecondStage(final String playerName) {
        final Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new SecondScene(this, playerName));
        stage.show();
    }

}
