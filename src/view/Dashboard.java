package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.Report;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @FXML private TableColumn<Appointment, String> appointmentStart;
    @FXML private TableColumn<Appointment, String> appointmentEnd;
    @FXML private ChoiceBox<String> range;
    @FXML private TableView<Report> reportTable;
    @FXML private TableColumn<Report, String> column1;
    @FXML private TableColumn<Report, String> column2;
    @FXML private TableColumn<Report, String> column3;
    @FXML private ChoiceBox<String> reportType;
    @FXML private ChoiceBox<String> consultant;
    private ObservableList<String> rangeItems = FXCollections.observableArrayList();
    private ObservableList<String> reportTypeList = FXCollections.observableArrayList();
    private List<String> reportList = Stream.of("Appointment Types", "Schedule by Consultant","Days by Load").collect(Collectors.toList());
    private List<String> rangeList = Stream.of("30", "7").collect(Collectors.toList());


    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerTable.setItems(Connector.getCustomerList());

        appointmentName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customer"));
        appointmentConsultant.setCellValueFactory(new PropertyValueFactory<Appointment, String>("userName"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<Appointment, String>("zonedStart"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<Appointment, String>("zonedEnd"));
        appointmentsTable.setItems(Connector.getAppointmentList());

        column1.setCellValueFactory(new PropertyValueFactory<Report, String>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<Report, String>("column2"));
        column3.setCellValueFactory(new PropertyValueFactory<Report, String>("column3"));
        Connector.reportListToAppointmentTypes();
        consultant.setDisable(true);
        column1.setText("Month");
        column2.setText("Type");
        column3.setText("Count");
        reportTable.setItems(Connector.getReportList());

        reportList.forEach(report -> reportTypeList.add(report)); //Lambda function for adding reports to the type list
        reportType.setItems(reportTypeList);
        reportType.setValue("Appointment Types");

        reportType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(t1.intValue() == 1) {
                    consultant.setDisable(false);
                    Connector.reportListToConsultant(consultant.getValue());
                    column1.setText("Customer Name");
                    column2.setText("Type");
                    column3.setText("Start");
                }
                else if(t1.intValue() == 2) {
                    consultant.setDisable(true);
                    Connector.reportListToLoad();
                    column1.setText("Day of Week");
                    column2.setText("Number of Appointments");
                    column3.setText("N/A");
                }
                else if(t1.intValue() == 0) {
                    consultant.setDisable(true);
                    Connector.reportListToAppointmentTypes();
                    column1.setText("Month");
                    column2.setText("Type");
                    column3.setText("Count");
                }
            }
        });

        consultant.setItems(Connector.getUserList());
        consultant.setValue(Connector.getUserList().get(0));
        consultant.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Connector.reportListToConsultant(Connector.getUserList().get(t1.intValue()));
            }
        });

        rangeList.forEach(range -> rangeItems.add(range)); //Lambda to simplify adding new range options
        range.setItems(rangeItems);
        range.setValue("30");
        range.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(range.getValue().equals("30"))
                    Connector.setRange(7);
                else
                    Connector.setRange(30);
                Connector.refreshAppointmentList();
            }
        });
        if(Connector.appointmentReminder())
            alertMe("Upcoming Appointment Reminder!");
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
        if(!(customerTable.getSelectionModel().getSelectedItem() == null)) {
            Optional<ButtonType> result = alertMe("Are you sure you want to delete this customer and any appointments held by the customer?");
            if(result.get() == ButtonType.OK)
                Connector.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
        }
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
        if(!(appointmentsTable.getSelectionModel().getSelectedItem() == null)) {
            Optional<ButtonType> result = alertMe("Are you sure you want to delete this appointment?");
            if(result.get() == ButtonType.OK)
                Connector.deleteAppointment(appointmentsTable.getSelectionModel().getSelectedItem());
        }
        else
            alertMe("No item selected.");
    }

    public void exitApplication() {
        Optional<ButtonType> result = alertMe("Are you sure you want to exit?");

        if(result.get() == ButtonType.OK)
            System.exit(0);
    }


}
