package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML private Label username;
    @FXML private Label password;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.227.166.251:3306/U07Ebi?profileSQL=true", "U07Ebi", "53689000795");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}