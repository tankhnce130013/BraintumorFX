package org.fpt.Controller.technician;

import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.FullTaskDTO;
import org.fpt.DTO.PatientDTO;
import org.fpt.DTO.TechnicianDTO;
import org.fpt.Model.DoctorModel;
import org.fpt.Model.FullTaskModel;
import org.fpt.Model.Operation;
import org.fpt.Model.PatientModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.fpt.Controller.technician.Home;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Edit implements Initializable {
    @FXML
    public VBox mainView;

    @FXML
    private Button btnBack;
    //Patient Information
    @FXML
    private TextField tfName;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private ComboBox<String> cbGender;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfHeathInsurance;
    @FXML
    private TextArea taNote;
    //Upload image
    @FXML
    private Label lbFileName;
    @FXML
    private Button btnUpload;
    @FXML
    private ComboBox<String> cbDoctor;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    //Error text
    @FXML
    private Text txtNameError;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtHeathInsuranceError;

    UUID taskId;
    FullTaskDTO fullTaskDTO = new FullTaskDTO();
    PatientDTO patient = new PatientDTO();
    ArrayList<DoctorDTO> listDoc = new ArrayList<>();
    TechnicianDTO technicianDTO = new TechnicianDTO();
    String dirFile = "";
    String nameFileZip = "";

    private void setSelectedDoctor(UUID doc){
        int choiseId = 0;
        int i = 0;
        for (DoctorDTO d: listDoc) {
            i++;
            cbDoctor.getItems().addAll(d.getFullName());
            if (d.getId() == doc){
                choiseId = i;
            }
        }
        cbDoctor.getSelectionModel().select(choiseId);
    }

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

    public void getData(UUID taskID, TechnicianDTO t){
        this.technicianDTO = t;
        this.taskId = taskID;
    }

    @FXML
    private void uploadData(){
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null){
                dirFile = selectedDirectory.getAbsolutePath();
                lbFileName.setText(selectedDirectory.getName());
            }
        } catch (Exception e){
            System.out.println("UploadImageFromDirection");
            System.out.println(e);
        }
    }

    private void zipDataFromDir(){
        try{
            String txtName = tfName.getText().trim();
            String patientName = txtName.trim().replaceAll("\\s+","");

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
            String formattedDate = myDateObj.format(myFormatObj);
            System.out.println("After formatting: " + formattedDate);

            File theDir = new File("C:\\program\\brain-tumor\\");
            if (!theDir.exists()){
                theDir.mkdirs();
            }

            nameFileZip = patientName+"-"+formattedDate;
            String fileName = "C:\\program\\brain-tumor\\" + nameFileZip + ".zip";

            FileOutputStream fos = new FileOutputStream(fileName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(dirFile);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            System.out.println("Zip file successful");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    @FXML
    private void onCancel(){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/TechnicianHome.fxml"
                            )
                    );
                    VBox pane = (VBox) loader.load();
                    mainView.getChildren().setAll(pane);
                    Home fooController = loader.getController();
                    fooController.getData(technicianDTO.getEmail());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void saveData(){
        boolean isNotError = true;

        String nameError = "Name can not be null!";
        String phoneError = "Phone can not be null!";
        String phoneErrorPattern = "Phone number must be 10 - 11 number";
        String healthError = "Health insurance can not be null!";
        String healthErrorPattern = "Wrong health insurance number!";

        String name = tfName.getText().trim();
        String birth = dpDateOfBirth.getValue().toString().trim();
        String gender = cbGender.getValue().trim();
        String phone = tfPhone.getText().trim();
        String heath = tfHeathInsurance.getText().trim();
        String note = taNote.getText().trim();

        if (name.equalsIgnoreCase("")){
            txtNameError.setText(nameError);
            isNotError = false;
        }

        if (phone.equalsIgnoreCase("")) {
            txtPhone.setText(phoneError);
            isNotError = false;
        } else if(!Pattern.matches("(84|0[1-9])+([0-9]{8,9})\\b", phone)){
            txtPhone.setText(phoneErrorPattern);
            isNotError = false;
        }

        if (heath.equalsIgnoreCase("")){
            txtHeathInsuranceError.setText(healthError);
            isNotError = false;
        } else if(!Pattern.matches("^[A-Z]{2}\\d{14}$", heath)){
            txtHeathInsuranceError.setText(healthErrorPattern);
            isNotError = false;
        }

        if (isNotError){
            //store data
            int i = cbDoctor.getSelectionModel().getSelectedIndex();
            UUID docId = listDoc.get(i).getId();

            Operation operation = new Operation();
            operation.updatePatientAndTask(name, birth, gender, phone, heath, nameFileZip, note, docId, fullTaskDTO.getId());
            try {
                //store data
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("");
                alert.setContentText("Your data have been saved!");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()){
                    if (result.get() == ButtonType.OK){
                        // ... user chose OK
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource(
                                        "/fxml/TechnicianHome.fxml"
                                )
                        );
                        VBox pane = (VBox) loader.load();
                        mainView.getChildren().setAll(pane);
                        Home fooController = loader.getController();
                        fooController.getData(technicianDTO.getEmail());
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backToMain(){
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            FullTaskModel   fullTaskModel   = new FullTaskModel();
            PatientModel    patientModel    = new PatientModel();
            DoctorModel     doctorModel     = new DoctorModel();

            listDoc     = doctorModel.loadDoctorOnline();
            fullTaskDTO = fullTaskModel.loadFullTask(taskId);
            patient     = patientModel.loadPatient(fullTaskDTO.getIdPatient());

            tfName.setText(patient.getFullname());
            setDataToDatepicker(patient.getDateOfBirth());
            loadChoiceBox(patient.getGender());
            tfPhone.setText(patient.getPhone());
            tfHeathInsurance.setText(patient.getHealthInsurance());
            taNote.setText(fullTaskDTO.getNote());
            setSelectedDoctor(fullTaskDTO.getDoctorID());
            nameFileZip = fullTaskDTO.getFolderName();
        });
    }
}
