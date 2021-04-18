package org.fpt.login;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    public AnchorPane root;
    public Circle profile_pic;
    public VBox mainview;

    @FXML
    private javafx.scene.control.Label lbImage;

    @FXML
    private javafx.scene.control.Button btnClose;
    @FXML
    private javafx.scene.control.Button btnMinimize;

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
    private javafx.scene.control.Button btnLogin;

    public void login_as_google(javafx.event.ActionEvent actionEvent) {
        String gClientId = "331364695130-s4nfgc0dr0imp4r6cv592hmdtekn8h07.apps.googleusercontent.com";
        String gRedir = "https://braintumorapplication.com/";
        String gScope = "https://www.googleapis.com/auth/userinfo.email";
        String gSecret = "ZdXUs7JwBhTmoVexrmXBQYTH";
        OAuthAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin(btnLogin, mainview);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Creating a graphic (image)
        Image img = new Image(getClass().getResource("/Image/pngtree-spring-medical-blue-minimalist-doctor-background-image_210583.png").toString());
        ImageView view = new ImageView(img);
        view.setFitHeight(568);
        view.setPreserveRatio(true);
        lbImage.setGraphic(view);
    }
}
