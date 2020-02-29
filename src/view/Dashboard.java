package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Connector;
import model.Customer;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Customer, String> customerId;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private TableColumn<Customer, String> customerPhone;
    @FXML private TableColumn<Customer, String> customerAddress;
    @FXML private TableColumn<Appointment, String> appointmentName;
    @FXML private TableColumn<Appointment, String> appointmentConsultant;
    @FXML private TableColumn<Appointment, String> appointmentType;
    @FXML private TableColumn<Appointment, String> appointmentDate;
    @FXML private TableColumn<Appointment, String> appointmentTime;

    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerTable.setItems(Connector.getCustomerList());

        appointmentName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customer"));
        appointmentConsultant.setCellValueFactory(new PropertyValueFactory<Appointment, String>("userName"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("date"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<Appointment, String>("time"));
        appointmentsTable.setItems(Connector.getAppointmentList());
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

    public void addCustomer(ActionEvent event) { loadScene("CustomerMenu.fxml", event); }

    public void modifyCustomer(ActionEvent event) {
        if(!(customerTable.getSelectionModel().getSelectedItem() == null)) {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Modify Customer");
            stage.setScene(new Scene(loader.load()));

            CustomerMenu controller = loader.getController();
            controller.initData(customerTable.getSelectionModel().getSelectedItem());

            stage.show();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else
            alertMe("No item selected.");
    }

    public void deleteCustomer(ActionEvent event) {
        if(!(customerTable.getSelectionModel().getSelectedItem() == null))
            Connector.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
        else
            alertMe("No item selected.");
    }

    public void addAppointment(ActionEvent event) { loadScene("AppointmentsMenu.fxml", event); }

    public void modifyAppointment(ActionEvent event) {
        if(!(appointmentsTable.getSelectionModel().getSelectedItem() == null)){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentsMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Modify Appointment");
            stage.setScene(new Scene(loader.load()));

            AppointmentsMenu controller = loader.getController();
            controller.initData(appointmentsTable.getSelectionModel().getSelectedItem());

            stage.show();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else
            alertMe("No item selected.");
    }

    public void deleteAppointment(ActionEvent event) {
        if(!(appointmentsTable.getSelectionModel().getSelectedItem() == null))
            Connector.deleteAppointment(appointmentsTable.getSelectionModel().getSelectedItem());
        else
            alertMe("No item selected.");
    }

    public void exitApplication() {
        Optional<ButtonType> result = alertMe("Are you sure you want to exit?");

        if(result.get() == ButtonType.OK)
            System.exit(0);
    }


}
