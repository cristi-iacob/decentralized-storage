package client.view.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UploadController {
    @FXML private TableView <MyDataType> informationTable;
    @FXML private Button uploadButton;
    @FXML private TableColumn <MyDataType, String> userColumn;
    @FXML private TableColumn <MyDataType, String> addressColumn;

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
        informationTable.setEditable(true);
        ObservableList < String > data =
                FXCollections.observableArrayList(
                        "user142",
                        "user385",
                        "user44",
                        "user399",
                        "user17",
                        "user233",
                        "user378",
                        "user461",
                        "user74",
                        "user279",
                        "user203",
                        "user82",
                        "user118",
                        "user495",
                        "user158"
                );
        ObservableList < String > data2 =
                FXCollections.observableArrayList(
                        "127.0.0.1:12142",
                        "127.0.0.1:12385",
                        "127.0.0.1:12044",
                        "127.0.0.1:12399",
                        "127.0.0.1:12017",
                        "127.0.0.1:12233",
                        "127.0.0.1:12378",
                        "127.0.0.1:12461",
                        "127.0.0.1:12074",
                        "127.0.0.1:12279",
                        "127.0.0.1:12203",
                        "127.0.0.1:12082",
                        "127.0.0.1:12118",
                        "127.0.0.1:12495",
                        "127.0.0.1:12158"
                );
        userColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUser()));
        addressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        ObservableList< MyDataType > list = FXCollections.observableArrayList();
        for (int i = 0; i < 15; ++ i) {
            list.add(new MyDataType(data.get(i), data2.get(i)));
        }
        informationTable.setItems(list);
    }
}
