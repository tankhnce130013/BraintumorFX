package org.fpt.Controller.doctor;

import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.FullTaskDTO;
import org.fpt.DTO.ImageDTO;
import org.fpt.DTO.PatientDTO;
import org.fpt.Model.FullTaskModel;
import org.fpt.Model.ImageModel;
import org.fpt.Model.PatientModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

public class EditTask implements Initializable
{
    @FXML
    public VBox mainView;

    //Patient information element
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
    // get list view image
    @FXML
    private ListView<Label> lvImage;
    // get button
    @FXML
    private Button btnBack;
    @FXML
    private Button btnFinish;
    @FXML
    private Button btnPredict;

    UUID taskID;
    DoctorDTO doctor;
    ArrayList<ImageDTO> listImage = new ArrayList<>();

    private double xOffset = 0;
    private double yOffset = 0;

    PatientDTO patient = new PatientDTO();
    FullTaskDTO fullTaskDTO = new FullTaskDTO();

    private String getTypeData(String data){
        switch (data){
            case "Above_Gli":
                return "Above glioma tumor";
            case "Front_Gli":
                return "Front glioma tumor";
            case "Side_Gli":
                return "Side glioma tumor";
            case "Above_Men":
                return "Above meningioma tumor";
            case "Front_Men":
                return "Front meningioma tumor";
            case "Side_Men":
                return "Side meningioma tumor";
            case "Above_Pi":
                return "Above pituitary tumor";
            case "Front_Pi":
                return "Front pituitary tumor";
            case "Side_Pi":
                return "Side pituitary tumor";
            case "Above_No":
                return "Above no tumor";
            case "Front_No":
                return "Front no tumor";
            case "Side_No":
                return "Side no tumor";
        }
        return null;
    }

    @FXML
    private void backToHome(){
        try {
            // create alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            // set title
            alert.setTitle("Alert");
            alert.setHeaderText("");
            // set content
            alert.setContentText("Are you sure?");
            // get button for alert
            Optional<ButtonType> result = alert.showAndWait();
            // check result is exist
            if (result.isPresent()){
                // user chose OK
                if (result.get() == ButtonType.OK){
                    // get xml file
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/DoctorHome.fxml"
                            )
                    );
                    // load to vbox
                    VBox pane = (VBox) loader.load();
                    // set vbox
                    mainView.getChildren().setAll(pane);
                    // get controller
                    HomeController fooController = loader.getController();
                    // get doctor data by email
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
            // create alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // set title
            alert.setTitle("Alert");
            alert.setHeaderText("");
            // set content
            alert.setContentText("Your data have been saved!");
            // get button for alert
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                // user chose OK
                if (result.get() == ButtonType.OK){
                    // get image model
                    ImageModel imageModel = new ImageModel();
                    // update all image in list
                    imageModel.updateTaskImage(listImage);
                    // load home xml
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "/fxml/DoctorHome.fxml"
                            )
                    );
                    // set vbox
                    VBox pane = (VBox) loader.load();
                    // set vbox element
                    mainView.getChildren().setAll(pane);
                    // get controller
                    HomeController fooController = loader.getController();
                    // pass data to home controller
                    fooController.getData(doctor.getEmail());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadDataToListview(){
        // load all image
        for (ImageDTO image: listImage){
            try {
                Label lbl = new Label("");
                lbl.setContentDisplay(ContentDisplay.LEFT);
                InputStream is = new ByteArrayInputStream(image.getData());
                BufferedImage ib = ImageIO.read(is);
                WritableImage wi = SwingFXUtils.toFXImage(ib, null);
                ImageView view = new ImageView(wi);
                view.setFitHeight(80);
                view.setPreserveRatio(true);
                Label lbType = new Label(getTypeData(image.getType()));
                lbType.setId("lbStyle");
                HBox imageBox = new HBox(view, lbType);
                imageBox.setPadding(new Insets(0,10,0,0));
                imageBox.setPrefWidth(440);
//                HBox.setMargin(imageBox, new Insets(0, 60, 0, 0));
                Button btn = new Button();
                btn.setText("Edit predict");
                btn.setId("btnListSegment");
                btn.setOnAction((ActionEvent event) -> {
                    ImageDTO img = image;
                    try {
                        // load xml file
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DoctorPredict.fxml"));
                        Parent root;
                        root = (Parent)fxmlLoader.load();
                        Predict predict = fxmlLoader.<Predict>getController();
                        predict.getData(img);
                        Stage stage = new Stage();
                        stage.setTitle("Predict");
                        stage.initStyle(StageStyle.UNDECORATED);
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
                        // create new scene
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.showAndWait();
                        if (predict.isSegment){
                            ImageDTO i = predict.image;
                            listImage.get(i.getId()).setType(i.getType());
                            lbType.setText(getTypeData(i.getType()));
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
                HBox hb = new HBox(imageBox, btn);
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.setPadding(new Insets(10));
                lbl.setGraphic(hb);
                lvImage.getItems().add(lbl);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void getTaskData(UUID idTask, DoctorDTO doc){
        this.taskID = idTask;
        this.doctor = doc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // wait for component to load
        Platform.runLater(() -> {
            PatientModel patientModel   = new PatientModel();
            FullTaskModel fullTaskModel = new FullTaskModel();
            ImageModel imageModel = new ImageModel();

            fullTaskDTO = fullTaskModel.loadFullTask(taskID);
            patient = patientModel.loadPatient(fullTaskDTO.getIdPatient());
            listImage = imageModel.loadAllImage(fullTaskDTO.getId());

            System.out.println(fullTaskDTO.getId().toString());
            System.out.println(listImage.size());

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
