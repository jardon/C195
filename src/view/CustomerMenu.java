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
import model.Customer;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerMenu implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField addressField2;
    @FXML private TextField zipField;
    @FXML private TextField phoneField;
    @FXML private ChoiceBox<String> cityChoiceBox;

    private boolean edited = false;
    private Customer customer;

    public void initialize(URL url, ResourceBundle rb) {
        cityChoiceBox.setItems(Connector.getCityList());
    }

    public void initData(Customer customer) {
        edited = true;
        this.customer = customer;
        nameField.setText(customer.getCustomerName());
        addressField.setText(customer.getAddress1());
        addressField2.setText(customer.getAddress2());
        zipField.setText(customer.getZip());
        phoneField.setText(customer.getPhone());
        cityChoiceBox.setValue(customer.getCity());
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

    public void saveCustomer(ActionEvent event) {
        if(edited) {
            customer.setCustomerName(nameField.getText());
            customer.setAddress1(addressField.getText());
            customer.setAddress2(addressField2.getText());
            customer.setCity(cityChoiceBox.getValue());
            customer.setZip(zipField.getText());
            customer.setPhone(phoneField.getText());
            Connector.updateCustomer(customer);
        }
        else
            Connector.addCustomer(
                    nameField.getText(),
                    addressField.getText(),
                    addressField2.getText(),
                    cityChoiceBox.getValue(),
                    zipField.getText(),
                    phoneField.getText());

        loadScene("Dashboard.fxml", event);
    }

    public void cancel(ActionEvent event) { loadScene("Dashboard.fxml", event); }
}
