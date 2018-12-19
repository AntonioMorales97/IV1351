package startup;

import integration.DBAccess;
import view.MainApp;

/**
 * This class handles the start up of the application. It sets up a connection
 * to the database and launches the GUI.
 *
 */
public class Startup {

    /**
     * Sets up connection to the database and launches the Application (GUI).
     * 
     * @param args Not used.
     */
    public static void main(String[] args) {
        try {
            DBAccess db = DBAccess.getDBAccess();
            db.connect();
            MainApp.launchApp();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
