module advalg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports advalg.model;
    exports advalg.view;
    exports advalg to javafx.graphics;

    opens advalg to javafx.fxml;
    opens advalg.controller to javafx.fxml;
}