package pa5.helpers.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pa5.PA_5_Source;

import java.io.IOException;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Controller {
    protected PA_5_Source _PA5Source;

    public void setMain(PA_5_Source PA5Source) {
        _PA5Source = PA5Source;
    }

    public static String getProgramPath() {
        String string = System.getProperty("user.dir");
        string = string.replace("\\", "/");
        return string;
    }

    protected abstract void setStage(Stage popupStage);

    protected void showNewWindow(FXMLLoader fxmlLoader, Controller controller) throws IOException {
        fxmlLoader.setController(controller);

        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        Stage popupStage = new Stage();

        controller.setStage(popupStage);
        popupStage.setResizable(false);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}
