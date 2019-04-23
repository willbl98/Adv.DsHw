module advalg {
    requires java.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens advalg to javafx.fxml;
    opens advalg.controller to javafx.fxml;

    exports advalg.model;
    exports advalg.view;
    exports advalg to javafx.graphics;
}