package client.view.controller;

import client.controller.ClientController;
import client.requests.RequestsSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class LoginController {
    @FXML private Button loginButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button registerButton;
    private RequestsSender requestsSender;

    public LoginController() {
        requestsSender = new RequestsSender();
    }

    public void login() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        CloseableHttpResponse response = requestsSender.loginRequest(username, password);
        HttpEntity entity = response.getEntity();
        try {
            String responseString = EntityUtils.toString(entity, "UTF-8");

            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 400) {
                ClientController clientController = new ClientController();
                clientController.setEmail(username);
                clientController.setUserToken(responseString);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/uploadPage.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root,700, 500);
                stage.setScene(scene);
                UploadController controller = loader.<UploadController>getController();
                controller.setClientController(clientController);
                stage.setOnCloseRequest(event -> {
                    requestsSender.logoutRequest(clientController.getEmail());
                });
                stage.show();
            } else if (response.getStatusLine().getStatusCode() == 406) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong username or password");
                alert.setContentText("Please try again");
                usernameTextField.clear();
                passwordTextField.clear();
                alert.show();
            } /*else if (response.getStatusLine().getStatusCode() == 400) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User already logged in");
                alert.setContentText("Please disconnect from other devices");
                usernameTextField.clear();
                passwordTextField.clear();
                alert.show();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerPage.fxml"));
        try {
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
