package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsMenu {

    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://3.227.166.251:3306/U07Ebi?profileSQL=true", "U07Ebi", "53689000795");
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    public void initData(Appointment appointment) {

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

    public void saveAppointment(ActionEvent event) {

    }

    public void cancel(ActionEvent event) {
        loadScene("Dashboard.fxml", event);
    }
}
