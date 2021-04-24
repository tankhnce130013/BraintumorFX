package org.fpt.Controller.doctor;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
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
    @FXML
    private Text txtReviewType;

    UUID taskID;
    DoctorDTO doctor;
    ArrayList<ImageDTO> listImage = new ArrayList<>();

    private double xOffset = 0;
    private double yOffset = 0;

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
        int column = 0;
        int row = 1;
        // load all image
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
                        txtReviewType.setText(getTypeData(image.getType()));
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
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onPredict(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DoctorPredict.fxml"));
            Parent root;
            root = (Parent)fxmlLoader.load();

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
                listImage.get(currentImage.getId()).setType(predictController.image.getType());
                currentButton.setId("btnAccept");
            }
        } catch (Exception e){
            e.printStackTrace();
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
