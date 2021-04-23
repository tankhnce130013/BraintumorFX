package org.fpt.Controller.doctor;

import org.fpt.DTO.DoctorDTO;
import org.fpt.DTO.HistoryDTO;
import org.fpt.DTO.TaskDTO;
import org.fpt.Model.DoctorModel;
import org.fpt.Model.TaskModel;
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
import org.fpt.Controller.doctor.EditTask;
import org.fpt.Controller.doctor.ViewTask;
import org.fpt.Controller.doctor.Profile;
import org.fpt.Controller.doctor.ViewTask;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    //Conmment element
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnLogout;
    @FXML
    public VBox mainView;
    @FXML
    public Text txtWel;
    //Task tab element
    @FXML
    private TextField tfTaskSearch;
    @FXML
    private Button btnTaskSearch;
    @FXML
    private TableView<TaskDTO> tvTask;
    @FXML
    private TableColumn<TaskDTO, String> colTaskNo;
    @FXML
    private TableColumn<TaskDTO, String> colTaskPatientName;
    @FXML
    private TableColumn<TaskDTO, String> colTaskGender;
    @FXML
    private TableColumn<TaskDTO, String> colTaskBirthDate;
    @FXML
    private TableColumn<TaskDTO, String> colTaskDate;
    @FXML
    private TableColumn<TaskDTO, String> colTaskStatus;
    @FXML
    private TableColumn<TaskDTO, String> colTaskAction;
    @FXML
    private Pagination paTask;

    //History tab element
    @FXML
    private TextField tfHisSearch;
    @FXML
    private Button btnHisSearch;
    @FXML
    private TableView<HistoryDTO> tvHis;
    @FXML
    private TableColumn<HistoryDTO, String> colHisNo;
    @FXML
    private TableColumn<HistoryDTO, String> colHisPatientName;
    @FXML
    private TableColumn<HistoryDTO, String> colHisGender;
    @FXML
    private TableColumn<HistoryDTO, String> colHisBirthDate;
    @FXML
    private TableColumn<HistoryDTO, String> colHisDate;
    @FXML
    private TableColumn<HistoryDTO, String> colHisStatus;
    @FXML
    private TableColumn<HistoryDTO, String> colHisAction;
    @FXML
    private Pagination paHis;
    //Array data
    ArrayList<TaskDTO> listTask = new ArrayList<>();
    ArrayList<HistoryDTO> listHistory = new ArrayList<>();
    //Item in one page
    int tCount = 1, hCount = 1;
    int tFrom = 0;
    int hFrom = 0;
    private int rowTaskPerPage = 10;
    private int rowHisPerPage = 10;
    //get Doctor
    DoctorDTO doc = new DoctorDTO();
    String email = "";

    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private void onLogout(){
        try {
            // get model
            DoctorModel doctorModel = new DoctorModel();
            // set status of doctor to off
            doctorModel.toggleDoctorOffline(email);

            // load Login xml code
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root;
            root = (Parent)fxmlLoader.load();
            // set stage of app
            Stage stage = new Stage();
            // set title and style of frame
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

            // set scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // show scene
            stage.show();
            // close current frame
            Stage stage1 = (Stage) btnLogout.getScene().getWindow();
            stage1.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupTaskTable()
    {
        // set collum name
        colTaskNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTaskPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTaskGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colTaskBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colTaskDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTaskStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        // set cell factory for table cell
        Callback<TableColumn<TaskDTO, String>, TableCell<TaskDTO, String>> cellFactory = new Callback<TableColumn<TaskDTO, String>, TableCell<TaskDTO, String>>() {
            @Override
            public TableCell<TaskDTO, String> call(final TableColumn<TaskDTO, String> param) {
                final TableCell<TaskDTO, String> cell = new TableCell<TaskDTO, String>() {
                    // set button for each cell
                    private final Button btn = new Button("Show");
                    {
                        // set action listener for button
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                // get index of cell
                                int index = getIndex();
                                TaskDTO task = listTask.get(index);
                                // get segment xml file
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/fxml/DoctorViewTask.fxml"
                                        )
                                );
                                // load to vbox component
                                VBox pane = (VBox) loader.load();
                                // set to vbox
                                mainView.getChildren().setAll(pane);
                                // get controller
                                ViewTask viewTaskController = loader.getController();
                                // pass data to segment's controller
                                viewTaskController.getTaskData(task.geteID(), doc);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        });
                        // set button id
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
        // set button to collum
        colTaskAction.setCellFactory(cellFactory);
    }

    private void setupHistoryTable()
    {
        // set collum name
        colHisNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHisPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colHisGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colHisBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colHisDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colHisStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        // set cell factory for table cell
        Callback<TableColumn<HistoryDTO, String>, TableCell<HistoryDTO, String>> cellFactory = new Callback<TableColumn<HistoryDTO, String>, TableCell<HistoryDTO, String>>() {
            @Override
            public TableCell<HistoryDTO, String> call(final TableColumn<HistoryDTO, String> param) {
                final TableCell<HistoryDTO, String> cell = new TableCell<HistoryDTO, String>() {
                    // set button for each cell
                    private final Button btn = new Button("Edit");
                    {
                        // set action listener for button
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                HistoryDTO hoDto = listHistory.get(getIndex());
                                // get segment xml file
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/fxml/DoctorEditTask.fxml"
                                        )
                                );
                                // load to vbox component
                                VBox pane = (VBox) loader.load();
                                // set to vbox
                                mainView.getChildren().setAll(pane);
                                // get controller
                                EditTask segmentController = loader.getController();
                                // pass data to segment's controller
                                segmentController.getTaskData(hoDto.geteID(), doc);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        });
                        // set button id
                        btn.setId("btnAction");
                    }
                    /**
                     * update and set graphic for button
                     * @param item
                     * @param empty
                     */
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
        // set button to collum
        colHisAction.setCellFactory(cellFactory);
    }

    @FXML
    private void profile(){
        try {
            // load xml file
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/DoctorProfile.fxml"
                    )
            );
            // load to vbox
            VBox pane = (VBox) loader.load();
            // set vbox
            mainView.getChildren().setAll(pane);
            // get profile controller
            Profile fooController = loader.getController();
            // pass data to profile controller
            fooController.getProfileData(doc, 0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onTaskSearch(){
        String searchText = tfTaskSearch.getText();
        if (searchText.equalsIgnoreCase("")){
            TaskModel taskModel = new TaskModel();
            tCount = taskModel.loadTaskNumbers(doc.getEmail());
            int pageTCount = tCount / rowTaskPerPage + 1;
            paTask.setPageCount(pageTCount);
            paTask.setPageFactory(this::createTaskPage);
        } else {
            TaskModel taskModel = new TaskModel();
            tCount = taskModel.searchTaskNumbers(doc.getEmail(), searchText);
            int pageHCount = tCount / rowHisPerPage + 1;
            paTask.setPageCount(pageHCount);
            paTask.setPageFactory(this::createSearchTaskPage);
        }
    }

    @FXML
    private void onHistorySearch(){
        // get data from search text field
        String searchText = tfHisSearch.getText();
        // check if search text field is empty or not
        if (searchText.equalsIgnoreCase("")){
            // get task model
            TaskModel taskModel = new TaskModel();
            // count task number by doctor email with status Completed
            hCount = taskModel.loadHistoryNumbers(doc.getEmail());
            // get number of page
            int pageHCount = hCount / rowHisPerPage + 1;
            // set page number
            paHis.setPageCount(pageHCount);
            // set history data for each page
            paHis.setPageFactory(this::createHisPage);
        } else {
            // get task model
            TaskModel taskModel = new TaskModel();
            // count task number by doctor email and key word with status Completed
            hCount = taskModel.searchHistoryNumbers(doc.getEmail(), searchText);
            // get number of page
            int pageHCount = hCount / rowHisPerPage + 1;
            // set page number
            paHis.setPageCount(pageHCount);
            // set history data for current page
            paHis.setPageFactory(this::createSearchHistoryTaskPage);
        }
    }

    private Node createSearchTaskPage(int pageIndex){
        // get data from search text field
        String searchText = tfTaskSearch.getText().trim();
        // clear array list
        listTask.clear();
        // get current page
        tFrom = pageIndex + 1;
        // load task model
        TaskModel taskModel = new TaskModel();
        // get data for current page
        listTask = taskModel.searchPagingTask(tFrom, doc.getEmail(), searchText);
        // set data to list view
        tvTask.setItems(FXCollections.observableArrayList(listTask));
        return tvTask;
    }

    private Node createSearchHistoryTaskPage(int pageIndex){
        // get data for search text field
        String searchText = tfHisSearch.getText().trim();
        // clear array list
        listHistory.clear();
        // get current page
        hFrom = pageIndex + 1;
        // load task model
        TaskModel taskModel = new TaskModel();
        // get data for current page
        listHistory = taskModel.searchPagingHistory(hFrom, doc.getEmail(), searchText);
        // set data to list view
        tvHis.setItems(FXCollections.observableArrayList(listHistory));
        return tvHis;
    }

    private Node createHisPage(int pageIndex){
        // clear array list
        listHistory.clear();
        // get current page
        hFrom = pageIndex + 1;
        // load task model
        TaskModel taskModel = new TaskModel();
        // get data for current page
        listHistory = taskModel.loadPagingHistory(hFrom, doc.getEmail());
        // set data to list view
        tvHis.setItems(FXCollections.observableArrayList(listHistory));
        return tvHis;
    }

    private Node createTaskPage(int pageIndex){
        // clear array list
        listTask.clear();
        // get current page
        tFrom = pageIndex + 1;
        // load task model
        TaskModel taskModel = new TaskModel();
        // get data for current page
        listTask = taskModel.loadPagingTask(tFrom, doc.getEmail());
        // set data to list view
        tvTask.setItems(FXCollections.observableArrayList(listTask));
        return tvTask;
    }

    public void getData(String email){
        this.email = email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // wait for component to load
        Platform.runLater(() -> {
            try {
                // set up data for task table view
                setupTaskTable();
                // set up data for search table view
                setupHistoryTable();
                // get doctor model
                DoctorModel dModel = new DoctorModel();
                //load all doctor by mail
                doc = dModel.loadDoctor(email);

                try {
                    // get current ip of computer
                    InetAddress IP =InetAddress.getLocalHost();
                    // set ip for doctor
                    dModel.updateDoctorIP(email, IP.getHostAddress());
                } catch (Exception e){
                    e.printStackTrace();
                }
                // change doctor status
                dModel.toggleDoctorOnline(email);
                // set welcome text
                txtWel.setText("Welcome Dr. "+doc.getFullName()+"!");
                // get task model
                TaskModel taskModel = new TaskModel();
                // get count of task and history
                tCount = taskModel.loadTaskNumbers(doc.getEmail());
                hCount = taskModel.loadHistoryNumbers(doc.getEmail());
                // get task page number
                int pageTCount = tCount / rowTaskPerPage + 1;
                // set page number and data
                paTask.setPageCount(pageTCount);
                paTask.setPageFactory(this::createTaskPage);
                // get history page number
                int pageHCount = hCount / rowHisPerPage + 1;
                // set page number data
                paHis.setPageCount(pageHCount);
                paHis.setPageFactory(this::createHisPage);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
