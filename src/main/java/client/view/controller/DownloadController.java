package client.view.controller;

import client.controller.ClientController;
import client.requests.RequestsSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.*;

public class DownloadController {
    @FXML private ListView<String> uploadedFilesListView;
    @FXML private Button downloadButton;
    @FXML private Button loadButton;
    private ClientController clientController;
    private RequestsSender requestsSender;

    public DownloadController() {
        requestsSender = new RequestsSender();
    }

    public ClientController getClientController() {
        return clientController;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @FXML public void download() {
        String selectedFile = uploadedFilesListView.getSelectionModel().getSelectedItem();
        String composedFilename = null;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("/home/cristi/scindo/" + clientController.getEmail() + "/uploaded.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("_");

                if (selectedFile.equals(parts[1])) {
                    composedFilename = line;
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = null;
        CloseableHttpResponse response = requestsSender.downloadRequest(composedFilename, clientController.getEmail());
        try {
            baos = new ByteArrayOutputStream();
            response.getEntity().writeTo(baos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileOutputStream stream = new FileOutputStream("/home/cristi/scindo/" + clientController.getEmail() + "/downloads/" + selectedFile)) {
            stream.write(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateUploads() {
        BufferedReader reader;
        ObservableList<String> items = FXCollections.observableArrayList();

        try {
            File file = new File("/home/cristi/scindo/" + clientController.getEmail() + "/uploaded.txt");
            file.createNewFile();
            reader = new BufferedReader(new FileReader("/home/cristi/scindo/" + clientController.getEmail() + "/uploaded.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("_");
                items.add(parts[1]);
                line = reader.readLine();
            }

            System.out.println(items);
            uploadedFilesListView.setItems(items);
            loadButton.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToUpload() {
        Stage stage = (Stage) downloadButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uploadPage.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            UploadController controller = loader.<UploadController>getController();
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
