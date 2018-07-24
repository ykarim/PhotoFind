import javafx.application.Application;
import org.photofind.db.VisionDatabase;
import org.photofind.ui.App;

public class PhotoFind {

    public static void main(String[] args) {
        //Load data from database
        VisionDatabase.start();

        //Launch UI
        App photoFindApp = new App();
        Application.launch(photoFindApp.getClass(), args); //editing pic name doesnt update imagebox
    }
}
