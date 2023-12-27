package start;

import javafx.application.Application;

/**
 * 
 */
@SuppressWarnings("PMD")
public final class Main {

    private Main() { }
     /**
     * @param args
     *             possible command line arguments (not used)
     */
    public static void main(final String[] args) {
        System.out.println("Chapo");
        Application.launch(LudoPartyApp.class, args);
    }

}
