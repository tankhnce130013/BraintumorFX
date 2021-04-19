package org.fpt.Controller.doctor;

import org.fpt.DTO.FullTaskDTO;
import org.fpt.DTO.ImageDTO;
import org.fpt.DTO.PatientDTO;
import org.fpt.DTO.*;
import org.fpt.Model.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.*;
import org.fpt.Controller.doctor.HomeController;
import org.fpt.Controller.doctor.Predict;
import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.FullTaskDTO;
import org.fpt.DTO.PatientDTO;
import org.fpt.Model.FullTaskModel;
import org.fpt.Model.PatientModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ViewTask implements Initializable {
    @FXML
    public VBox mainView;

    //Patient infor
    @FXML
    private Label lbName;
    @FXML
    private Label lbDateOfBirth;
    @FXML
    private Label lbGender;
    @FXML
    private Label lbPhone;
    @FXML
    private Label lbHealthInsurance;
    @FXML
    private TextArea taNote;

    @FXML
    private ListView<Label> lvImage;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnFinish;
    @FXML
    private Button btnPredict;

    String zipName = "hung";
    //get id of doctor and task
    UUID taskID;
    DoctorDTO doctor = new DoctorDTO();
    ArrayList<ImageDTO> listImage          = new ArrayList<>();
    ArrayList<ImageDTO> listPredictImage = new ArrayList<>();

    PatientDTO patient = new PatientDTO();
    FullTaskDTO fullTaskDTO = new FullTaskDTO();

    private double xOffset = 0;
    private double yOffset = 0;

    private String getRedictData(String url){
        System.out.println(url);
        int index = url.lastIndexOf("\\");
        String fileName = url.substring(index + 1);
        System.out.println("File name: " + fileName);
        String predict ="";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image",fileName,
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                   new File(url) ))
                    .build();
            Request request = new Request.Builder()
                    .url("http://118.69.53.27:8089/predict")
                    .method("POST", body)
                    .addHeader("image", "")
                    .build();
            Response response = client.newCall(request).execute();

            predict = response.body().string();
            System.out.println(predict);
        } catch (Exception e){
            e.printStackTrace();
        }
        return predict;
    }

    @FXML
    private void backToHome(){
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
                                    "/fxml/DoctorHome.fxml"
                            )
                    );
                    VBox pane = (VBox) loader.load();
                    mainView.getChildren().setAll(pane);
                    HomeController fooController = loader.getController();
                    fooController.getData(doctor.getEmail());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void finishToHome(){
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.setContentText("Your data have been saved!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    ImageModel imageModel = new ImageModel();
                    imageModel.saveTaskImage(listPredictImage, taskID.toString());
                    TaskModel taskModel = new TaskModel();
                    taskModel.completeTask(taskID);
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/DoctorHome.fxml"
                            )
                    );
                    VBox pane = (VBox) loader.load();
                    mainView.getChildren().setAll(pane);
                    HomeController fooController = loader.getController();
                    fooController.getData(doctor.getEmail());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadDataToListview(){
        try {
            unzipDataFromDir();
        } catch (Exception e){
            e.printStackTrace();
        }

        int id = 0;
        // file unzip
        System.out.println(fullTaskDTO.getFolderName());
        File selectedDirectory = new File("/Users/macbookair/Desktop/BrainTumor/unzip/"+ fullTaskDTO.getFolderName());
        System.out.println("File length " + selectedDirectory.exists());
        if (selectedDirectory.exists()){
            File[] files = selectedDirectory.listFiles();
            if (files.length != 0){
                for (File f : files){
                    try {
                        String fullPath = f.getAbsolutePath();
                        String fileExtension = fullPath.substring(fullPath.lastIndexOf('.'));
                        if (fileExtension.equals(".png") || fileExtension.equals(".jpg") || fileExtension.equals(".PNG") || fileExtension.equals(".JPG")){
                            FileInputStream fin = new FileInputStream(f);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] buf = new byte[1024];
                            for (int readNum; (readNum = fin.read(buf)) != -1;){
                                bos .write(buf, 0,readNum);
                            }
                            ImageDTO img = new ImageDTO(id,null, f.getAbsolutePath(), bos.toByteArray(), "", "");
                            listImage.add(img);
                            id++;
                        }
                    } catch (Exception e){
                        System.out.println(e);
                    }
                }

                for (ImageDTO image: listImage){
                    try {
                        Label lbl = new Label("");
                        lbl.setContentDisplay(ContentDisplay.LEFT);
                        InputStream is      = new ByteArrayInputStream(image.getData());
                        BufferedImage ib    = ImageIO.read(is);
                        WritableImage wi    = SwingFXUtils.toFXImage(ib, null);

                        ImageView view = new ImageView(wi);
                        view.setFitHeight(80);
                        view.setPreserveRatio(true);

                        HBox imageBox = new HBox(view);
                        HBox.setMargin(imageBox, new Insets(0, 300, 0, 0));

                        Button btn = new Button();
                        btn.setText("Predict");
                        btn.setId("btnListSegment");


                        Label lblCheck =  new Label("");
                        Image imgDoc = new Image(getClass().getResource("/Image/1500737-middle.png").toString());
                        ImageView viewDoc = new ImageView(imgDoc);
                        viewDoc.setFitHeight(40);
                        viewDoc.setPreserveRatio(true);


                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DoctorPredict.fxml"));
                                Parent root;
                                root = (Parent)fxmlLoader.load();

                                String type = getRedictData(image.getFullPath());

                                image.setType(type);

                                Predict predictController = fxmlLoader.<Predict>getController();
                                predictController.getData(image);

                                Stage stage = new Stage();
                                stage.setTitle("Predict");
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.getIcons().add(new Image(getClass().getResource("/Image/Brain.png").toString()));
                                //grab your root here
                                root.setOnMousePressed(e -> {
                                    xOffset = e.getSceneX();
                                    yOffset = e.getSceneY();
                                });
                                //move around here
                                root.setOnMouseDragged(e -> {
                                    stage.setX(e.getScreenX() - xOffset);
                                    stage.setY(e.getScreenY() - yOffset);
                                });

                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.showAndWait();
                                if (predictController.isSegment){
                                    lblCheck.setGraphic(viewDoc);
                                    listPredictImage.add(predictController.image);
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        });

                        HBox contentBox = new HBox(btn, lblCheck);
                        contentBox.setAlignment(Pos.CENTER_LEFT);
                        contentBox.setSpacing(10);
                        contentBox.setPadding(new Insets(10,20,10,10));

                        HBox hb = new HBox(imageBox, contentBox);
                        hb.setAlignment(Pos.CENTER_LEFT);
                        hb.setPadding(new Insets(10));

                        lbl.setGraphic(hb);
                        lvImage.getItems().add(lbl);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void unzipDataFromDir() throws IOException {
        // file zip
        String dirName      = "/Users/macbookair/Desktop/BrainTumor/zip/"+ fullTaskDTO.getFolderName() +".zip";
        // file unzip
        File destDir        = new File("/Users/macbookair/Desktop/BrainTumor/unzip/");
        if (destDir.exists()){
            byte[] buffer       = new byte[1024];
            ZipInputStream zis  = new ZipInputStream(new FileInputStream(dirName));
            ZipEntry zipEntry   = zis.getNextEntry();
            while (zipEntry != null) {
                while (zipEntry != null) {
                    File newFile = newFile(destDir, zipEntry);
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Failed to create directory " + newFile);
                        }
                    } else {
                        // fix for Windows-created archives
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Failed to create directory " + parent);
                        }
                        // write file content
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                    zipEntry = zis.getNextEntry();
                }
            }
            zis.closeEntry();
            zis.close();
        }
    }

    public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public void getTaskData(UUID idTask, DoctorDTO doc){
        this.taskID = idTask;
        this.doctor = doc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            PatientModel patientModel   = new PatientModel();
            FullTaskModel fullTaskModel = new FullTaskModel();

            fullTaskDTO = fullTaskModel.loadFullTask(taskID);
            patient = patientModel.loadPatient(fullTaskDTO.getIdPatient());
            lbName.setText(patient.getFullname());
            lbDateOfBirth.setText(patient.getDateOfBirth());
            lbGender.setText(patient.getGender());
            lbPhone.setText(patient.getPhone());
            lbHealthInsurance.setText(patient.getHealthInsurance());
            taNote.setText(fullTaskDTO.getNote());
            LoadDataToListview();
        });
    }
}
