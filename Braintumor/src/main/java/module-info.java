module org.fpt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;

    opens org.fpt to javafx.fxml;
    exports org.fpt;
}