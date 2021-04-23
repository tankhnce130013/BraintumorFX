package org.fpt.Controller.doctor;

import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.TechnicianDTO;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.fpt.Controller.doctor.HomeController;
import org.fpt.Controller.doctor.ProfileEdit;
import org.fpt.Controller.technician.Home;

import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {
    int type = -1;

    @FXML
    public VBox mainView;
    @FXML
    private Text txtGender;
    @FXML
    private Label tblAvatar;
    @FXML
    private Text txtName;
    @FXML
    private Text txtDateOfBirth;
    @FXML
    private Label txtSpecialty;
    // get doctor
    DoctorDTO doc = new DoctorDTO();
    TechnicianDTO technicianDTO = new TechnicianDTO();

    @FXML
    private void backToHome(){
        switch (type) {
            case 0:
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/DoctorHome.fxml"
                            )
                    );
                    VBox pane = (VBox) loader.load();
                    mainView.getChildren().setAll(pane);
                    HomeController fooController = loader.getController();
                    fooController.getData(doc.getEmail());
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 1:
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/TechnicianHome.fxml"
                            )
                    );
                    VBox pane = (VBox) loader.load();
                    mainView.getChildren().setAll(pane);
                    Home fooController = loader.getController();
                    fooController.getData(technicianDTO.getEmail());
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
        }
    }

    @FXML
    private void goToEditProfile(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/DoctorEditProfile.fxml"
                    )
            );
            VBox pane = (VBox) loader.load();
            mainView.getChildren().setAll(pane);
            ProfileEdit fooController = loader.getController();
            switch (this.type){
                case 0:
                    fooController.getProfileData(doc, 0);
                    break;
                case 1:
                    fooController.getProfileData(technicianDTO, 1);
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getProfileData(Object obj, int id)
    {
        this.type = id;
        switch (this.type){
            case 0:
                this.doc = ((DoctorDTO) obj);
                break;
            case 1:
                this.technicianDTO = ((TechnicianDTO) obj);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Some code
        Platform.runLater(() -> {
            //do stuff
            switch (type){
                case 0:
                    txtName.setText(doc.getFullName());
                    txtGender.setText(doc.getGender());
                    txtDateOfBirth.setText(doc.getBirthdate());
                    txtSpecialty.setText(doc.getSpecialist());
                    //Creating a graphic (image)
                    Image imgDoc = new Image(getClass().getResource("/Image/a4040890a14cf4de8ecea56265f66cf7.png").toString());
                    ImageView viewDoc = new ImageView(imgDoc);
                    viewDoc.setFitHeight(189);
                    viewDoc.setPreserveRatio(true);
                    tblAvatar.setGraphic(viewDoc);
                    break;
                case 1:
                    txtName.setText(technicianDTO.getFullName());
                    txtGender.setText(technicianDTO.getGender());
                    txtDateOfBirth.setText(technicianDTO.getBirthdate());
                    txtSpecialty.setText(technicianDTO.getSpecialist());
                    //Creating a graphic (image)
                    Image imgTech = new Image(getClass().getResource("/Image/a4040890a14cf4de8ecea56265f66cf7.png").toString());
                    ImageView viewTech = new ImageView(imgTech);
                    viewTech.setFitHeight(189);
                    viewTech.setPreserveRatio(true);
                    tblAvatar.setGraphic(viewTech);
                    break;
            }
        });
    }
}
