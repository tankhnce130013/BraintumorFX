package org.fpt.Controller.technician;

import com.jfoenix.controls.JFXDatePicker;
import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.TechnicianDTO;
import org.fpt.Entity.Doctor;
import org.fpt.Model.DoctorModel;
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

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Add implements Initializable {
    @FXML
    private Button btnBack;
    //Patient Information
    @FXML
    private TextField tfName;
    @FXML
    private JFXDatePicker dpDateOfBirth;
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
    private Text txtPhoneError;
    @FXML
    private Text txtHeathInsuranceError;
    @FXML
    private VBox mainView;

    //Infor
    ArrayList<DoctorDTO> listDoc = new ArrayList<>();
    TechnicianDTO technicianDTO = new TechnicianDTO();
    String dirFile = "";
    String nameFileZip = "Linh-12-04-2021-11-23-05";
    DoctorDTO doctor;

    private void setDataToDatepicker(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        dpDateOfBirth.setValue(LocalDate.of(year, month, day));
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
            e.printStackTrace();
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

//            File theDir = new File("C:\\program\\brain-tumor\\");
//            if (!theDir.exists()){
//                theDir.mkdirs();
//            }

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



    @FXML
    private void UploadImageFromDirection(){
        int id = 0;
        try {

        } catch (Exception e){
            System.out.println("UploadImageFromDirection");
            System.out.println(e);
        }
    }

    private void loadDataToDoctor(){
        for (DoctorDTO d: listDoc) {
            cbDoctor.getItems().addAll(d.getFullName());
        }
        cbDoctor.getSelectionModel().select(0);
    }

    private void loadGenderData(){
        cbGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        cbGender.getSelectionModel().select(0);
    }

    @FXML
    private void saveData(){
        boolean isNotError = true;

        String nameError = "Name can not be null!";
        String phoneError = "Phone can not be null!";
        String phoneErrorPattern = "Phone number must be 10 - 11 number!";
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
            txtPhoneError.setText(phoneError);
            isNotError = false;
        } else if(!Pattern.matches("(84|0[1-9])+([0-9]{8})\\b", phone)){
            txtPhoneError.setText(phoneErrorPattern);
            isNotError = false;
        }

        if (heath.equalsIgnoreCase("")){
            txtHeathInsuranceError.setText(healthError);
            isNotError = false;
        } else if(!Pattern.matches("^[A-Z]{2}\\d{14}$", heath)){
            txtHeathInsuranceError.setText(healthErrorPattern);
            isNotError = false;
        }

        if (dirFile.equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("");
            alert.setContentText("You forgot to choose file!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == ButtonType.OK){

                }
            }
            isNotError = false;
        }

        if (isNotError){
            //store data
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    Operation operation = new Operation();
                    int i = cbDoctor.getSelectionModel().getSelectedIndex();
                    UUID docId = listDoc.get(i).getId();
                    doctor = listDoc.get(i);
                    //sent zip to Sever Path
                    Sharing(); // Net USE MAPPING DRIVE

//                    String patientName =  name.trim().replaceAll("\\s+", "");
//                    LocalDateTime myDateObj = LocalDateTime.now();
//                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH_mm_ss");
//                    String formattedDate = myDateObj.format(myFormatObj);
//                    String fileName = patientName+formattedDate;

                    String recive = "";
                    String zipName = zipDataFromDir(name,dirFile);

                    String uuid= doctor.getId().toString();
                    int lastSlash = uuid.lastIndexOf("-");
                    int lastSlash_ = dirFile.lastIndexOf("\\");
                    String base_dir = dirFile.substring(0,lastSlash_);
                    String sent_ =base_dir+"\\"+zipName;
                    System.out.println(dirFile);
                    String doctorInfo = doctor.getFullName().trim().replaceAll("\\s+", "")+"_"+uuid.substring(lastSlash+1);
                    recive = "\\\\TECHNICIAN\\Data\\Technician\\Doctor\\"+doctorInfo;
                    File file = new File(recive);
                    if(!file.isDirectory()){
                        file.mkdirs();
                    }
                    CopyToShaingFolder(sent_,recive);

                    operation.addPatientAndTask(name, birth, gender, phone, heath, zipName, note, docId, technicianDTO.getId());
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
            }
        }
    }

    public static void CopyToShaingFolder(String sent_path, String recive_path) {

        String command = "cmd.exe /c Copy  \"" + sent_path + "\"  \"" + recive_path + "\"";
        System.out.println(command);
        try {
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader BR = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String l;
            while ((l = BR.readLine()) != null) {
                System.out.print(l);
            }
            p.destroy();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    /*
    execute copy file form technician PC to sharing folder
    @pagram: path local path folder file of patient
    step 1 : zip the folder to zip file to Braintumor/dataZip

     */
    public  void Sharing() {
        String folder = "\\\\TECHNICIAN\\Data";
        String command = "NET USE E: " + folder;
        System.out.println(command);
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader BR = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String l;
            while ((l = BR.readLine()) != null) {
                System.out.print(l);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private  String zipDataFromDir(String name, String dirFile) {
        try {
            String patientName = name.trim().replaceAll("\\s+", "");

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH_mm_ss");
            String formattedDate = myDateObj.format(myFormatObj);
            System.out.println("After formatting: " + formattedDate);


            //rename the folder to key of patient
            int lastSlash = dirFile.lastIndexOf("\\");
            String base_dir = dirFile.substring(0,lastSlash);
            String BaseFolderName = dirFile.substring(lastSlash+1);
            String fileName = base_dir +"\\"+ patientName + "_" + formattedDate+".zip";


            String command = "cmd.exe /c cd "+base_dir+" && rename " + BaseFolderName+" "+patientName+"_"+formattedDate;
            dirFile = base_dir+"\\"+patientName+"_"+formattedDate;
            System.out.println(command);
            try {
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader BR = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String l;
                while ((l = BR.readLine()) != null) {
                    System.out.print(l);
                }
                System.out.println("success");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            FileOutputStream fos = new FileOutputStream(fileName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(dirFile);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            System.out.println("Zip file successful");
            return patientName + "_" + formattedDate + ".zip";
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Fail";
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
            System.out.println(length);
        }
        fis.close();
    }



    @FXML
    private void backToMain(){
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

    public void getData(TechnicianDTO t){
        this.technicianDTO = t;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            loadGenderData();

            DoctorModel doctorModel = new DoctorModel();
            listDoc = doctorModel.loadDoctorOnline();

            loadDataToDoctor();
            setDataToDatepicker();
        });
    }
}
