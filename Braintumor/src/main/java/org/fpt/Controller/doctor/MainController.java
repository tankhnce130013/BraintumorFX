package org.fpt.Controller.doctor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.fpt.Model.DoctorModel;
import org.fpt.Model.ImageModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fpt.Controller.doctor.HomeController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    @FXML
    private ImageView imgIcon;

    private String email;

    @FXML
    private void onclose(){
        DoctorModel doctorModel = new DoctorModel();
        doctorModel.toggleDoctorOffline(email);
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
        Image image = new Image(getClass().getResource("/Image/Brain.png").toString());
        imgIcon.setImage(image);
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/DoctorHome.fxml"
                        )
                );
                VBox pane = (VBox) loader.load();
                mainView.getChildren().setAll(pane);
                HomeController fooController = loader.getController();
                fooController.getData(email);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}
