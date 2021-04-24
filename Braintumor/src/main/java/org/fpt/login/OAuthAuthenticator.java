package org.fpt.login;

import org.fpt.Controller.doctor.MainController;
import org.fpt.Entity.Doctor;
import org.fpt.Entity.Task;
import org.fpt.Entity.Technician;
import org.fpt.Model.DoctorModel;
import org.fpt.Model.Operation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.fpt.Model.TaskModel;
import org.json.JSONObject;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public abstract class OAuthAuthenticator {

    private JSONObject accessedJsonData;

    private boolean gotData = false;
    private boolean attemptRecieved = false;
    private boolean loginAttempted = false;

    private String accessToken;
    private String accessCode;

    private final String clientID;
    private final String redirectUri;
    private final String clientSecret;
    private static String signInMail = "";
    private Stage stage;

    private double xOffset = 0;
    private double yOffset = 0;


    public OAuthAuthenticator(String clientID, String redirectUri, String clientSecret) {
        this.clientID = clientID;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    private final boolean isLogin = false;

    public void startLogin(Button btn, VBox mainview) {
        loginAttempted = true;
        stage = new Stage();
        Image image = new Image(getClass().getResource("/Image/Brain.png").toString());
        stage.getIcons().add(image);
        mainview.setDisable(true);
        WebView root = new WebView();
        stage.setOnCloseRequest(e -> {
            mainview.setDisable(false);
        });
        WebEngine engine = root.getEngine();
        engine.load(getWebUrl());
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                try {
                    if (engine.getLocation().contains("https://braintumorapplication.com/")) {
                        attemptRecieved = true;

                        String location = engine.getLocation();

                        accessCode = location.substring(location.indexOf("code=") + 5);

                        accessToken = doGetAccessTokenRequest(accessCode);

                        String returnedJson = doGetAccountInfo(accessToken);

                        accessedJsonData = new JSONObject(returnedJson);
                        String email = (String) accessedJsonData.get("email");

                        Operation operation = new Operation();
                        int index = operation.checkLogin(email);

                        try {
                            if (index == 1) {
                                signInMail = email;
                                //Get connection to Sharing file folder
                                String command = "cmd.exe /c NET USE E: \\\\TECHNICIAN\\Data  /u:hntan1769@mail.com ZtenQw@1769";

                                try {
                                    Process p = Runtime.getRuntime().exec(command);
                                    p.destroy();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                //start autobot
                                Runnable runnable = new autoUnzipRunnable(); // or an anonymous class, or lambda...
                                Thread thread = new Thread(runnable);
                                thread.start();

                                //load FXML
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DocterMain.fxml"));
                                Parent root;
                                root = fxmlLoader.load();

                                MainController fooController = fxmlLoader.getController();
                                fooController.getData(email);
                                Stage stage = new Stage();
                                stage.setTitle("Home");
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
                                stage.show();

                                Stage stage1 = (Stage) btn.getScene().getWindow();
                                stage1.close();
                            } else if (index == 2) {
                                //Get connection to Sharing file folder
                                String command = "cmd.exe /c NET USE E: \\\\TECHNICIAN\\Data  /u:hntan1769@mail.com ZtenQw@1769";

                                try {
                                    Process p = Runtime.getRuntime().exec(command);
                                    p.destroy();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();

                                }
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TechnicianMain.fxml"));
                                Parent root;
                                root = fxmlLoader.load();

                                org.fpt.Controller.technician.MainController fooController = fxmlLoader.getController();
                                fooController.getData(email);
                                Stage stage = new Stage();
                                stage.setTitle("Home");
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
                                stage.show();

                                Stage stage1 = (Stage) btn.getScene().getWindow();
                                stage1.close();

                            } else {
                                closeStage();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Login fail");
                                alert.setHeaderText("");
                                alert.setContentText("Wrong email !!!");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.isPresent()) {
                                    if (result.get() == ButtonType.OK) {
                                        // ... user chose OK
                                        mainview.setDisable(false);
                                    }
                                }
                            }

                            gotData = true;

                            closeStage();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (engine.getLocation().contains("https://accounts.google.com/signin/oauth/error")) {
                        closeStage();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login fail");
                        alert.setHeaderText("");
                        alert.setContentText("Wrong email !!!");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent()) {
                            if (result.get() == ButtonType.OK) {
                                // ... user chose OK
                                mainview.setDisable(false);
                            }
                        }

                    }

                }catch (NullPointerException e){
                    mainview.setDisable(false);
                    e.printStackTrace();
                }
            }
        });

/**
 engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
 public void handle(WebEvent<String> event) {
 if(gotData || attemptRecieved) {
 return;
 }
 if (event.getSource() instanceof WebEngine) {
 WebEngine we = (WebEngine) event.getSource();
 String location = we.getLocation();
 if (location.contains("code") && location.startsWith(getRedirectUri())) {
 attemptRecieved = true;
 //closeStage();
 accessCode = location.substring(location.indexOf("code=") + 5);

 accessToken = doGetAccessTokenRequest(accessCode);

 String returnedJson = doGetAccountInfo(accessToken);

 accessedJsonData = new JSONObject(returnedJson);

 System.out.println(returnedJson);

 gotData = true;

 notifyLoginViewCompleted();
 }
 }
 }
 });
 **/

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
        do checking doctor change PC for work
        check is name of PC is changed
        if changed do re-storage data from storage folder at sharing folder in Mapping network
     */


    public class autoUnzipRunnable implements Runnable {
        DoctorModel doctorModel = new DoctorModel();

        public void run() {
            Doctor doctor;
            if (signInMail != "") {
                //Setting up info
                // Checking PC Name
                DoctorModel doctorModel = new DoctorModel();
                doctor = doctorModel.loadEntityDoctor(signInMail);
                String curHostName = "";
                String cmd = "cmd.exe /c cmd /k hostname";
                try {
                    Process p = Runtime.getRuntime().exec(cmd);
                    BufferedReader BR = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String l;
                    while ((l = BR.readLine()) != null) {
                        curHostName = l;
                    }
                    //checking is new PC or Not
                    if (!doctor.getIp().equalsIgnoreCase(curHostName)) {
                        //do restorage data from unfinished task
                        RestorageTask(doctor);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //DO update PC to SQL
                doctorModel.updateDoctorIP(doctor.getEmail(),curHostName);
                // preparing data for AUTObot
                String uuid = doctor.getId().toString();
                int lastSlash = uuid.lastIndexOf("-");
                String doctorInfo = doctor.getFullName().trim().replaceAll("\\s+", "") + "_" + uuid.substring(lastSlash + 1);
                File folder = new File("\\\\TECHNICIAN\\Data\\Technician\\Doctor\\" + doctorInfo+"\\");
                String outPath = "Data\\Doctor\\" + doctorInfo+"\\";
                String storagePath = "\\\\TECHNICIAN\\Data\\Storage\\" + doctorInfo + "\\";

                while (true) {
                    try {
                        File storageDir = new File(storagePath);
                        if (!storageDir.isDirectory()) {
                            storageDir.mkdirs();
                        }

                        File[] files = folder.listFiles();
                        for (File file : files) {
                            int lastDot = file.getName().lastIndexOf(".");
                            String fileExtension = file.getName().substring(lastDot);
                            if (file.isFile() && fileExtension.equalsIgnoreCase(".zip")) {
                                unzipDataFromDir(file.getPath(), outPath);
                                CutFile(file.getPath(), storagePath);
                            }
                        }
                        Thread.sleep(5000);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void RestorageTask(Doctor doctor) throws IOException {
            TaskModel taskModel = new TaskModel();
            ArrayList<Task> tasks = taskModel.getAllPendingTaskByDoctorUUID(doctor.getId());
            //check is empty
            if (!tasks.isEmpty()) {
                //do unzip
                String uuid = doctor.getId().toString();
                int lastSlash = uuid.lastIndexOf("-");
                String doctorInfo = doctor.getFullName().trim().replaceAll("\\s+", "") + "_" + uuid.substring(lastSlash + 1);
                File folder = new File("\\\\TECHNICIAN\\Data\\Storage\\" + doctorInfo + "\\");
                String folder_path = "\\\\TECHNICIAN\\Data\\Storage\\" + doctorInfo + "\\";
                String outPath = "Data/Doctor/" + doctorInfo;
                String storagePath = "\\\\TECHNICIAN\\Data\\Storage\\" + doctorInfo + "\\";
                for (Task task : tasks) {
                    File zipFile = new File(folder_path+task.getFolderName());
                    if (zipFile.exists()) {
                        unzipDataFromDir(folder_path, outPath);
                    }
                }
            }
        }

        public void CutFile(String sent_path, String recive_path) {

            String command = "cmd.exe /c move  " + sent_path + "  " + recive_path;

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

        public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
            File destFile = new File(destinationDir, zipEntry.getName());

            String destDirPath = destinationDir.getCanonicalPath();
            String destFilePath = destFile.getCanonicalPath();

            if (!destFilePath.startsWith(destDirPath + File.separator)) {
                throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
            }

            return destFile;
        }

        private void unzipDataFromDir(String dirName, String pathOut) throws IOException {
            // file zip
//        String dirName      = "/Users/macbookair/Desktop/BrainTumor/zip/"+ fullTaskDTO.getFolderName() +".zip";
            // file unzip
//        File destDir        = new File("/Users/macbookair/Desktop/BrainTumor/unzip/");

            File destDir = new File(pathOut);
            if (!destDir.isDirectory()) {
                destDir.mkdirs();
            }
            if (destDir.exists()) {
                byte[] buffer = new byte[1024];
                ZipInputStream zis = new ZipInputStream(new FileInputStream(dirName));
                ZipEntry zipEntry = zis.getNextEntry();
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

    private void unzipDataFromDir(String dirName, String pathOut) throws IOException {
        // file zip
//        String dirName      = "/Users/macbookair/Desktop/BrainTumor/zip/"+ fullTaskDTO.getFolderName() +".zip";
        // file unzip
//        File destDir        = new File("/Users/macbookair/Desktop/BrainTumor/unzip/");

        File destDir = new File(pathOut);
        if (!destDir.isDirectory()) {
            destDir.mkdirs();
        }
        if (destDir.exists()) {
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(dirName));
            ZipEntry zipEntry = zis.getNextEntry();
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


    abstract String getWebUrl();

    abstract String getApiTokenUrl();

    abstract String getApiAccessUrl();

    abstract String getApiAccessParams();

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public boolean hasFinishedSuccessfully() {
        return gotData;
    }

    public JSONObject getJsonData() {
        if (gotData) {
            return accessedJsonData;
        } else {
            return null;

        }
    }

    private void closeStage() {
        stage.close();
    }

    private void notifyLoginViewCompleted() {
        if (gotData) {
            //LoginView.getInstance().completedOAuthLogin(this);
        }
    }

    private String doGetAccountInfo(String accessToken) {
        try {
            HttpURLConnection connection2 = null;
            URL url2 = new URL(getApiTokenUrl());
            connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection2.setDoInput(true);
            connection2.setDoOutput(true);


            int reponseCode2 = connection2.getResponseCode();

            if (reponseCode2 == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in2 = new BufferedReader(new InputStreamReader(
                        connection2.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();

                while ((inputLine2 = in2.readLine()) != null) {
                    response2.append(inputLine2);
                }
                in2.close();
                connection2.disconnect();
                return response2.toString();
            } else {
                System.err.println("Error retrieving api data!: " + reponseCode2);
            }
        } catch (IOException e) {
            System.err.println("####### ERROR GETTING ACCOUNT INFO ##############");
        }
        return null;
    }

    private String doGetAccessTokenRequest(String code) {
        try {
            URL url = new URL(getApiAccessUrl());
            String urlParams = getApiAccessParams();


            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + postDataLength);
            connection.setRequestProperty("Connection", "keep-alive");
            //connection.setRequestProperty("Referer", "https://accounts.google.com/o/oauth2/token");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);

            wr.flush();

            int responseCode = connection.getResponseCode();



            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                System.err.println("Success getting access token for OAuth Login!");
            } else {
                System.err.println("Error getting access token for OAuth Login!");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            String fullResponse = response.toString();

            JSONObject json = new JSONObject(fullResponse);

            String accessToken = json.getString("access_token");

            return accessToken;
        } catch (IOException e) {
        }
        return null;
    }
}

