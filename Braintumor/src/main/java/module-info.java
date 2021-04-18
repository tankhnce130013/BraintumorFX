module org.fpt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires javafx.web;
    requires javafx.swing;
    requires okhttp3;

    opens org.fpt to javafx.fxml;
    exports org.fpt ;

}