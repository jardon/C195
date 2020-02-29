package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Connector;
import model.User;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML private Label username;
    @FXML private Label password;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    Connection conn;

    public void initialize(URL url, ResourceBundle rb) {
        Connector.load();
    }

    private void loadScene(String destination, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Optional<ButtonType> alertMe(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.initModality(Modality.NONE);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public void loginAction(ActionEvent event) {
        int uid = Connector.checkCreds(usernameField.getText(), passwordField.getText());
        if(uid >= 0) {
            User.login(usernameField.getText(), uid, true);
            loadScene("Dashboard.fxml", event);
        }
        else
            alertMe("Incorrect Login");
    }

}