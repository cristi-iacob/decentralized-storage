package client.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class DownloadController {
    @FXML private ListView<String> uploadedFilesListView;
    @FXML private Button downloadButton;

    @FXML public void download() {
        ObservableList<String> items = FXCollections.observableArrayList("doggo.png", "photo.png");
        this.uploadedFilesListView.setItems(items);
    }
}
