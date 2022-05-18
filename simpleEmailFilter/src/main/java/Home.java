import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Home extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL re = getClass().getResource("Operations.fxml");
        Parent root = FXMLLoader.load(re);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
