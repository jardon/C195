package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Connector;
import model.Customer;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsMenu implements Initializable {

    @FXML private TextField typeField;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> customerId;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> timeChoiceBox;
    @FXML private ChoiceBox<String> consultantChoiceBox;
    @FXML private Spinner<Integer> duration;
    private ObservableList<String> timeSlots = FXCollections.observableArrayList();

    private boolean edited = false;
    private Appointment appointment;

    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerTable.setItems(Connector.getCustomerList());
        consultantChoiceBox.setItems(Connector.getUserList());
        duration.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4,1));

        for(int i = 7; i < 18; i++) {
            String time;
            if(i < 10)
                time = "0" + i;
            else
                time = Integer.toString(i);
            timeSlots.add(time + ":00:00");
            timeSlots.add(time + ":15:00");
            timeSlots.add(time + ":30:00");
            timeSlots.add(time + ":45:00");
        }
        timeChoiceBox.setItems(timeSlots);
    }

    public void initData(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        edited = true;
        typeField.setText(appointment.getType());
        consultantChoiceBox.setValue(appointment.getUserName());
        this.appointment = appointment;
        customerTable.getSelectionModel().select(Connector.getCustomer(appointment.getCustomerId()));
        duration.getValueFactory().setValue(Duration.between(appointment.getStart(), appointment.getEnd()).toHoursPart());
        timeChoiceBox.setValue(appointment.getStartAsString().substring(11) + ":00");
        datePicker.setValue(LocalDate.parse(appointment.getStartAsString().substring(0,10), formatter));
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
        String time = String.format("%s %s", datePicker.getValue(), timeChoiceBox.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(edited) {
            appointment.setCustomerId(customerTable.getSelectionModel().getSelectedItem().getIntId());
            appointment.setUserId(Connector.getUserId(consultantChoiceBox.getSelectionModel().getSelectedItem()));
            appointment.setType(typeField.getText());
            appointment.setStart(LocalDateTime.parse(time, formatter));
            appointment.setEnd(LocalDateTime.parse(time, formatter).plusHours(duration.getValue()));
            Connector.updateAppointment(appointment);
        }
        else
            Connector.addAppointment(
                    customerTable.getSelectionModel().getSelectedItem().getCustomerId(),
                    consultantChoiceBox.getValue(),
                    typeField.getText(),
                    time,
                    LocalDateTime.parse(time, formatter).plusHours(duration.getValue()).toString());

        loadScene("Dashboard.fxml", event);
    }

    public void cancel(ActionEvent event) {
        loadScene("Dashboard.fxml", event);
    }
}
