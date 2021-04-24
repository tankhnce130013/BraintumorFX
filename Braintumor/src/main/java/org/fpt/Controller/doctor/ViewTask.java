package org.fpt.Controller.doctor;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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

    String getImageFullPath = "";
    ImageDTO currentImage = new ImageDTO();
    Button currentButton = new Button();

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView imgReview;

    private double xOffset = 0;
    private double yOffset = 0;

    private String getRedictData(String url){
        int index = url.lastIndexOf("\\");
        String fileName = url.substring(index + 1);
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
        File selectedDirectory = new File("/Users/macbookair/Desktop/BrainTumor/unzip/"+ fullTaskDTO.getFolderName());
        if (selectedDirectory.exists()){
            File[] files = selectedDirectory.listFiles();
            if (files.length != 0){
                for (File f : files){
                    try {
                        String fullPath = f.getAbsolutePath();
                        String fileExtension = fullPath.substring(fullPath.lastIndexOf('.'));
                        if (fileExtension.equals(".png") || fileExtension.equals(".jpg")|| fileExtension.equals(".jpeg") || fileExtension.equals(".PNG") || fileExtension.equals(".JPG")|| fileExtension.equals(".JPEG")){
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

                int column = 0;
                int row = 1;

                int index  = 0;

                for (ImageDTO image: listImage){
                    try {
                        Button btn = new Button();

                        InputStream is      = new ByteArrayInputStream(image.getData());
                        BufferedImage ib    = ImageIO.read(is);
                        WritableImage wi    = SwingFXUtils.toFXImage(ib, null);

                        ImageView view = new ImageView(wi);
                        view.setFitHeight(90);
                        view.setPreserveRatio(true);

                        btn.setGraphic(view);
                        btn.setPadding(new Insets(5, 5, 5, 5));

                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                imgReview.setImage(wi);
                                imgReview.setPreserveRatio(true);
                                currentImage = image;
                                getImageFullPath = image.getFullPath();
                                currentButton = btn;
                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        });

                        VBox menuButtons = new VBox();
                        menuButtons.getChildren().setAll(btn);


                        if (column == 6) {
                            column = 0;
                            row++;
                            HBox.setMargin(menuButtons, new Insets(0, 0, 0, 0));
                        } else {
                            HBox.setMargin(menuButtons, new Insets(0, 50, 0, 0));
                        }

                        grid.add(menuButtons, column++, row); //(child,column,row)
                        //set grid width
                        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        grid.setMaxWidth(Region.USE_PREF_SIZE);

                        //set grid height
                        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        grid.setMaxHeight(Region.USE_PREF_SIZE);

                        GridPane.setMargin(menuButtons, new Insets(10));

                        if (index == 0) {
                            imgReview.setImage(wi);
                            imgReview.setPreserveRatio(true);
                            currentImage = image;
                            getImageFullPath = image.getFullPath();
                            currentButton = btn;
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    index ++;
                }
            }
        }
    }

    @FXML
    private void onPredict(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DoctorPredict.fxml"));
            Parent root;
            root = (Parent)fxmlLoader.load();

            String type = getRedictData(getImageFullPath);

            currentImage.setType(type);

            Predict predictController = fxmlLoader.<Predict>getController();
            predictController.getData(currentImage);

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
                listPredictImage.add(predictController.image);
                currentButton.setId("btnAccept");
            }
        } catch (Exception e){
            e.printStackTrace();
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
