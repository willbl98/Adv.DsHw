package pa5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pa5.controller.MainUICtrl;
import pa5.model.ToDoList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("view/pa5.fxml"));
        MainUICtrl mainUICtrl = new MainUICtrl();
        fxmlLoader.setController(mainUICtrl);
        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        // Here for testing convenience
        ToDoList toDoList = new ToDoList();
        System.out.println("Max Value: " + toDoList.calcMaxProfit());
        System.out.println(toDoList.findAllSchedules());
        System.exit(0);
    }
}
