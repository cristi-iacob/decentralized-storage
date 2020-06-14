package client.view.controller;

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


public class RegisterController {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField passwordConfirmationTextField;
    @FXML private Button registerButton;
    private RequestsSender requestsSender;

    public RegisterController() {
        requestsSender = new RequestsSender();
    }

    public void register() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String passwordConfirmation = passwordConfirmationTextField.getText();

        if (!password.equals(passwordConfirmation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Passwords are different.");
            alert.setContentText("Please correct them.");
            alert.show();
            passwordConfirmationTextField.clear();
            passwordTextField.clear();
            return;
        }

        CloseableHttpResponse respone = requestsSender.registerRequest(username, password);
        HttpEntity entity = respone.getEntity();

        try {
            if (respone.getStatusLine().getStatusCode() == 200) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Registration was successful");

                Stage stage = (Stage) registerButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginPage.fxml"));
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("There is another user with this username");
                alert.setContentText("Please choose another username");
                alert.show();
                usernameTextField.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
