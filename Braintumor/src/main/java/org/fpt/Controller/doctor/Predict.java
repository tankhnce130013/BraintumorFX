package org.fpt.Controller.doctor;

import javafx.scene.image.Image;
import org.fpt.DTO.ImageDTO;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Predict implements Initializable {
    @FXML
    private Text txtName;
    @FXML
    private ImageView imgTumor;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    public ImageDTO image = new ImageDTO();
    public boolean isSegment = false;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;
    @FXML
    private ImageView imgIcon;

    ArrayList<String> listType = new ArrayList<>();

    @FXML
    private void onclose(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onMinimize(){
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onCancel(){
        isSegment = false;
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    Stage stage = (Stage) btnClose.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onSave(){
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.setContentText("Your data have been saved!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    image.setType(getDataComboBox(cbType.getValue()));
                    isSegment = true;
                    Stage stage = (Stage) btnClose.getScene().getWindow();
                    stage.close();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getDataComboBox(String data){
        switch (data){
            case "Above glioma tumor":
                return "Above_Gli";
            case "Front glioma tumor":
                return "Front_Gli";
            case "Side glioma tumor":
                return "Side_Gli";
            case "Above meningioma tumor":
                return "Above_Men";
            case "Front meningioma tumor":
                return "Front_Men";
            case "Side meningioma tumor":
                return "Side_Men";
            case "Above pituitary tumor":
                return "Above_Pi";
            case "Front pituitary tumor":
                return "Front_Pi";
            case "Side pituitary tumor":
                return "Side_Pi";
            case "Above no tumor":
                return "Above_No";
            case "Front no tumor":
                return "Front_No";
            case "Side no tumor":
                return "Side_No";
        }
        return null;
    }

    private void loadDataOfComboBox(){
        for (String type: listType) {
            cbType.getItems().addAll(type);
        }
    }

    private void setIndexComboBox(String data){
        switch (data){
            case "Above_Gli":
                cbType.getSelectionModel().select(0);
                break;
            case "Front_Gli":
                cbType.getSelectionModel().select(1);
                break;
            case "Side_Gli":
                cbType.getSelectionModel().select(2);
                break;
            case "Above_Men":
                cbType.getSelectionModel().select(3);
                break;
            case "Front_Men":
                cbType.getSelectionModel().select(4);
                break;
            case "Side_Men":
                cbType.getSelectionModel().select(5);
                break;
            case "Above_Pi":
                cbType.getSelectionModel().select(6);
                break;
            case "Front_Pi":
                cbType.getSelectionModel().select(7);
                break;
            case "Side_Pi":
                cbType.getSelectionModel().select(8);
                break;
            case "Above_No":
                cbType.getSelectionModel().select(9);
                break;
            case "Front_No":
                cbType.getSelectionModel().select(10);
                break;
            case "Side_No":
                cbType.getSelectionModel().select(11);
                break;
        }
    }

    private void loadImage(byte[] img){
        try {
            InputStream is = new ByteArrayInputStream(img);
            BufferedImage ib = ImageIO.read(is);
            WritableImage image = SwingFXUtils.toFXImage(ib, null);
            imgTumor.setImage(image);
            imgTumor.setFitHeight(300);
            imgTumor.setPreserveRatio(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadCombobox(String data){
        if (data.equalsIgnoreCase("Meningioma Tumor")){
            cbType.getSelectionModel().select(0);
        } else if(data.equalsIgnoreCase("Pituitary Tumor")){
            cbType.getSelectionModel().select(1);
        } else {
            cbType.getSelectionModel().select(2);
        }
    }

    public void getData(ImageDTO obj){
        this.image = obj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image2 = new Image(getClass().getResource("/Image/Brain.png").toString());
        imgIcon.setImage(image2);
        Platform.runLater(() -> {
            listType.add("Above glioma tumor");
            listType.add("Front glioma tumor");
            listType.add("Side glioma tumor");
            listType.add("Above meningioma tumor");
            listType.add("Front meningioma tumor");
            listType.add("Side meningioma tumor");
            listType.add("Above pituitary tumor");
            listType.add("Front pituitary tumor");
            listType.add("Side pituitary tumor");
            listType.add("Above no tumor");
            listType.add("Front no tumor");
            listType.add("Side no tumor");

            loadDataOfComboBox();
            loadImage(image.getData());
            loadCombobox(image.getType());
            setIndexComboBox(image.getType());
        });
    }
}
