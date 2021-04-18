module org.fpt {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.fpt to javafx.fxml;
    exports org.fpt;
}