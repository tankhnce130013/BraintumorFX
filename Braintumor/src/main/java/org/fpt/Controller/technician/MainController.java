package org.fpt.Controller.technician;

import org.fpt.Model.DoctorModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fpt.Controller.technician.Add;
import org.fpt.Controller.technician.Home;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //Conmment element
    @FXML
    public VBox mainView;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;

    private String email;

    @FXML
    private void onclose(){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void onMinimize(){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void getData(String email){
        this.email = email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/TechnicianHome.fxml"
                        )
                );
                VBox pane = (VBox) loader.load();
                mainView.getChildren().setAll(pane);
                //org.fpt.Controller.technician.Home fooController = loader.getController();
                org.fpt.Controller.technician.Home fooController = new Home();
                fooController.getData(email);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}
