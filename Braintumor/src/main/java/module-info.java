module org.fpt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires javafx.web;
    requires javafx.swing;
    requires okhttp3;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.graphicsEmpty;
    //requires kotlin.runtime;

    opens org.fpt to javafx.fxml;
    opens org.fpt.login to javafx.fxml;
    opens org.fpt.Controller.technician to javafx.fxml;
    opens org.fpt.Controller.doctor to javafx.fxml;
    opens org.fpt.DTO to javafx.base;
    opens org.fpt.Model to javafx.base;
    opens org.fpt.Entity to javafx.base;

    exports org.fpt ;
}