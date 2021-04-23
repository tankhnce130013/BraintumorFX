package org.fpt.Controller.technician;

import org.fpt.DTO.TechnicianDTO;
import org.fpt.DTO.tTaskDTO;
import org.fpt.Model.TechnicianModel;
import org.fpt.Model.tTaskModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.fpt.Controller.doctor.Profile;
import org.fpt.Controller.technician.Add;
import org.fpt.Controller.technician.Edit;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable {
    //Big component
    @FXML
    public VBox mainView;
    //Basic component
    @FXML
    public Text txtWel;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnLogout;
    @FXML
    private TextField tfSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAddPatient;

    //Task tab element
    @FXML
    private TableView<tTaskDTO> tvTask;
    @FXML
    private TableColumn<tTaskDTO, String> colTaskNo;
    @FXML
    private TableColumn<tTaskDTO, String> colTaskPatientName;
    @FXML
    private TableColumn<tTaskDTO, String> colTaskDoctorName;
    @FXML
    private TableColumn<tTaskDTO, String> colTaskDate;
    @FXML
    private TableColumn<tTaskDTO, String> colTaskStatus;
    @FXML
    private TableColumn<tTaskDTO, String> colTaskAction;
    @FXML
    private Pagination paTask;
    //lis data
    private ArrayList<tTaskDTO> listTask = new ArrayList<>();
    // pagination
    int tCount = 1;
    int tFrom = 0, tTo = 0;
    private final static int rowPerPage = 10;
    // technician data
    TechnicianDTO technicianDTO = new TechnicianDTO();
    String email = "";
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void onLogout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root;
            root = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
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

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Stage stage1 = (Stage) btnLogout.getScene().getWindow();
            stage1.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupTaskTable(){
        colTaskNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTaskPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTaskDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        colTaskDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTaskStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        Callback<TableColumn<tTaskDTO, String>, TableCell<tTaskDTO, String>> cellFactory = new Callback<TableColumn<tTaskDTO, String>, TableCell<tTaskDTO, String>>() {
            @Override
            public TableCell<tTaskDTO, String> call(final TableColumn<tTaskDTO, String> param) {
                final TableCell<tTaskDTO, String> cell = new TableCell<tTaskDTO, String>() {

                    private final Button btn = new Button("Edit");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/fxml/TechnicianEdit.fxml"
                                        )
                                );
                                VBox pane = (VBox) loader.load();
                                mainView.getChildren().setAll(pane);
                                Edit fooController = loader.getController();
                                fooController.getData(listTask.get(getIndex()).geteID(), technicianDTO);
                            } catch (Exception e){
                                System.out.println(e);
                            }
                        });
                        btn.setId("btnAction");
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colTaskAction.setCellFactory(cellFactory);
    }

    @FXML
    private void profile(){
        try {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/DoctorProfile.fxml"
                        )
                );
                VBox pane = (VBox) loader.load();
                mainView.getChildren().setAll(pane);
                Profile fooController = loader.getController();
                fooController.getProfileData(technicianDTO, 1);
            } catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void addPatient(){
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/TechnicianAdd.fxml"
                    )
            );
            VBox pane = (VBox) loader.load();
            mainView.getChildren().setAll(pane);
            Add fooController = loader.getController();
            fooController.getData(technicianDTO);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private Node createPage(int pageIndex){
        listTask.clear();
        tFrom = pageIndex + 1;
        tTaskModel taskModel = new tTaskModel();
        listTask = taskModel.loadPagingHistory(tFrom, technicianDTO.getEmail());
        tvTask.setItems(FXCollections.observableArrayList(listTask));
        return tvTask;
    }

    @FXML
    private void onTaskSearch(){
        String searchText = tfSearch.getText();
        if (searchText.equalsIgnoreCase("")){
            tTaskModel taskModel = new tTaskModel();
            tCount = taskModel.loadHistoryNumbers(technicianDTO.getEmail());
            int pageTCount = tCount / rowPerPage + 1;
            paTask.setPageCount(pageTCount);
            paTask.setPageFactory(this::createPage);
        } else {
            tTaskModel taskModel = new tTaskModel();
            tCount = taskModel.searchHistoryNumbers(technicianDTO.getEmail(), searchText, searchText);
            int pageHCount = tCount / rowPerPage + 1;
            paTask.setPageCount(pageHCount);
            paTask.setPageFactory(this::createSearchTaskPage);
        }
    }

    private Node createSearchTaskPage(int pageIndex){
        String searchText = tfSearch.getText();
        listTask.clear();
        tFrom = pageIndex + 1;

        tTaskModel taskModel = new tTaskModel();
        listTask = taskModel.searchPagingHistory(tFrom, email, searchText, searchText);

        tvTask.setItems(FXCollections.observableArrayList(listTask));
        return tvTask;
    }

    public void getData(String email){
        this.email = email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            setupTaskTable();
            TechnicianModel technicianModel = new TechnicianModel();
            technicianDTO = technicianModel.loadTechnician(email);
            int pageTCount = tCount / rowPerPage + 1;
            paTask.setPageCount(pageTCount);
            paTask.setPageFactory(this::createPage);
            tTaskModel taskModel = new tTaskModel();
            tCount = taskModel.loadHistoryNumbers(technicianDTO.getEmail());
            txtWel.setText("Welcome Dr. "+ technicianDTO.getFullName()+"!");
        });
    }
}
