package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Connector;
import model.User;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML private Label username;
    @FXML private Label password;
    @FXML private Label loginMessage;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button login;
    private String error;

    public void initialize(URL url, ResourceBundle rb) {

        Connector.load();
        rb = ResourceBundle.getBundle("localization/login", Locale.getDefault());
        username.setText(rb.getString("username") + ": ");
        password.setText(rb.getString("password") + ": ");
        login.setText(rb.getString("login"));
        loginMessage.setText(rb.getString("loginMessage"));
        error = rb.getString("error");
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
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("logins.txt", true));
                writer.write(String.format("[%s] user %s logged in", LocalDateTime.now().toString(), User.getUsername()));
                writer.close();
            }
            catch (Exception e) {
            System.out.println("Login.loginAction: " + e);
            }

            loadScene("Dashboard.fxml", event);
        }
        else
            alertMe(error);
    }

}