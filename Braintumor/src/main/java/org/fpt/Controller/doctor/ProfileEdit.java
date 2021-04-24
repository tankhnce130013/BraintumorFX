package org.fpt.Controller.doctor;

import com.jfoenix.controls.JFXDatePicker;
import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.TechnicianDTO;
import org.fpt.Model.DoctorModel;
import org.fpt.Model.TechnicianModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.fpt.Controller.technician.Home;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileEdit implements Initializable {
    int type;
    // Common element
    @FXML
    public VBox mainView;
    @FXML
    private TextField tfName;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private JFXDatePicker dpDateOfBirth;
    @FXML
    private TextField tfSpecialty;
    // Error message
    @FXML
    private Text errorName;
    @FXML
    private Text errorSpecialty;
    @FXML
    private Text txtDateErr;
    //data
    DoctorDTO doc = new DoctorDTO();
    TechnicianDTO technicianDTO = new TechnicianDTO();

    private void loadChoiceBox(String data){
        int index = -1;
        cbGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        if(data.equalsIgnoreCase("Male")){
            index = 0;
        } else if(data.equalsIgnoreCase("Female")){
            index = 1;
        } else {
            index = 2;
        }
        cbGender.getSelectionModel().select(index);
    }

    private void setDataToDatepicker(String data){
        String year = "";
        String mounth = "";
        String day = "";

        String s = data;
        String[] splits = s.split("-");

        year = splits[0];
        mounth = splits[1];
        day = splits[2];

        dpDateOfBirth.setValue(LocalDate.of(Integer.parseInt(year), Integer.parseInt(mounth), Integer.parseInt(day)));
    }

    @FXML
    private void saveData(){
        String name = tfName.getText().trim();
        String specialty = tfSpecialty.getText().trim();

        if (name.equalsIgnoreCase("")){
            errorName.setText("Please enter Doctor's Name");
        }

        if (specialty.equalsIgnoreCase("")){
            errorSpecialty.setText("Please enter Doctor's position");
        }

        if (dpDateOfBirth.getValue().toString().equalsIgnoreCase("")){
            txtDateErr.setText("Please choose patient date of birth!");
        }

        if (!specialty.equalsIgnoreCase("") &&
            !name.equalsIgnoreCase("") &&
                !dpDateOfBirth.getValue().toString().equalsIgnoreCase("")){
            // Add Data to data base
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.setContentText("Your data have been saved!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                try {
                    LocalDate localDate = dpDateOfBirth.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);

                    String fullname  = tfName.getText().trim();
                    String specalist = tfSpecialty.getText().trim();
                    String birthdate = localDate.toString().trim();
                    String gender = cbGender.getValue();

                    switch (type) {
                        case 0:
                            try {
                                String email = doc.getEmail();
                                DoctorModel dModel = new DoctorModel();
                                dModel.updateDoctor(fullname, specalist, birthdate, gender, email);
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/fxml/DoctorHome.fxml"
                                        )
                                );
                                VBox pane = (VBox) loader.load();
                                mainView.getChildren().setAll(pane);
                                HomeController fooController = loader.getController();
                                fooController.getData(email);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                String email = technicianDTO.getEmail();
                                TechnicianModel technicianModel = new TechnicianModel();
                                technicianModel.updateTechnician(fullname, specalist, birthdate, gender, email);

                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/fxml/TechnicianHome.fxml"
                                        )
                                );
                                VBox pane = (VBox) loader.load();
                                mainView.getChildren().setAll(pane);
                                Home fooController = loader.getController();
                                fooController.getData(email);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void cancelData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
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
                        String email = technicianDTO.getEmail();
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                        "/fxml/TechnicianHome.fxml"
                                )
                        );
                        VBox pane = (VBox) loader.load();
                        mainView.getChildren().setAll(pane);
                        Home fooController = loader.getController();
                        fooController.getData(email);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
            }
        } else {
            // ... user chose CANCEL or closed the dialog
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
        Platform.runLater(() -> {
            switch (type){
                case 0:
                    tfName.setText(doc.getFullName());
                    loadChoiceBox(doc.getGender());
                    tfSpecialty.setText(doc.getSpecialist());
                    setDataToDatepicker(doc.getBirthdate());
                    break;
                case 1:
                    tfName.setText(technicianDTO.getFullName());
                    loadChoiceBox(technicianDTO.getGender());
                    tfSpecialty.setText(technicianDTO.getSpecialist());
                    setDataToDatepicker(technicianDTO.getBirthdate());
                    break;
            }
        });
    }
}
