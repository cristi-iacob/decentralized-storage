package client.view.controller;

import client.controller.ClientController;
import client.requests.RequestsSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UploadController {
    @FXML private TableView <MyDataType> informationTable;
    @FXML private Button uploadButton;
    @FXML private Button selectFileButton;
    @FXML private TableColumn <MyDataType, String> userColumn;
    @FXML private TableColumn <MyDataType, String> addressColumn;
    @FXML private Label fileLabel;
    private File selectedFile;
    private RequestsSender requestsSender;
    private ClientController clientController;

    public UploadController() {
        requestsSender = new RequestsSender();
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    private class MyDataType {
        private String user;
        private String address;

        public MyDataType(String user, String address) {
            this.user = user;
            this.address = address;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @FXML void upload() {
        CloseableHttpResponse response = requestsSender.uploadRequest(selectedFile, clientController.getEmail());
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                clientController.addFileToUploaded(responseString.split("~")[0]);

                ArrayList<MyDataType> uploadInformation = new ArrayList<>();
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                List<String> users = gson.fromJson(responseString.split("~")[1], type);
                ObservableList < MyDataType > items = FXCollections.observableArrayList();
                userColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUser()));
                addressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
                for (String user : users) {
                    items.add(new MyDataType(user, "localhost:" + (Integer.parseInt(user.split("user")[1]) + 12000)));
                }
                informationTable.setItems(items);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void selectFile() {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(selectFileButton.getScene().getWindow());
        fileLabel.setText(selectedFile.getName());
    }

    public void goToDownload() {
        Stage stage = (Stage) uploadButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/downloadPage.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            DownloadController controller = loader.<DownloadController>getController();
            controller.setClientController(clientController);
            stage.setOnCloseRequest(event -> {
                requestsSender.logoutRequest(clientController.getEmail());
            });
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
