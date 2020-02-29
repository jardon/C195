package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class Connector {

    static Connection conn;
    static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    static ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
    static ObservableList<String> cityList = FXCollections.observableArrayList();
    static ObservableList<String> userList = FXCollections.observableArrayList();

    public static void load() {
        try {
            System.out.println("Attempting to connect to DB.....");
            conn = DriverManager.getConnection("jdbc:mysql://3.227.166.251:3306/U07Ebi?profileSQL=true", "U07Ebi", "53689000795");
        }
        catch (Exception e) {
        System.out.println(e);
        }
    }

    public static int checkCreds(String username, String password) {
        try {
            ResultSet loginData = conn.createStatement().executeQuery(String.format("SELECT userName, userId, password, active FROM user WHERE userName = '%s'", username));
            loginData.next();
            if(password.equals(loginData.getString("password")) && loginData.getInt("active") == 1)
                return Integer.parseInt(loginData.getString("userId"));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return -1;
    }

    public static void addCustomer(String name, String address1, String address2, String city, String zip, String phone) {
        try {
            ResultSet cityInfo = conn.createStatement().executeQuery(String.format("SELECT cityId FROM city WHERE city = '%s'",city));
            cityInfo.next();

            conn.createStatement().executeUpdate(String.format(
                    "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES ('%s','%s',%s,'%s','%s',CURDATE(),'%s',CURDATE(),'%s')",
                    address1,
                    address2,
                    cityInfo.getString("cityId"),
                    zip,
                    phone,
                    User.getUsername(),
                    User.getUsername()));

            ResultSet addressInfo = conn.createStatement().executeQuery(String.format(
                    "SELECT addressId FROM address " +
                    "WHERE address = '%s' AND address2 = '%s' AND cityId = %s AND postalCode = '%s'" ,
                    address1,
                    address2,
                    cityInfo.getString("cityId"),
                    zip));
            addressInfo.next();

            conn.createStatement().executeUpdate(String.format(
                    "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES ('%s',%s,1,CURDATE(),'%s',CURDATE(),'%s')",
                    name,
                    addressInfo.getString("addressId"),
                    User.getUsername(),
                    User.getUsername()));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateCustomer(Customer customer) {
        try {
            ResultSet locationInfo = conn.createStatement().executeQuery(String.format(
                    "SELECT\n" +
                    "address.addressId,\n" +
                    "city.cityId\n" +
                    "FROM customer\n" +
                    "INNER JOIN address ON customer.addressId = address.addressId\n" +
                    "INNER JOIN city ON city.cityId = address.cityId\n" +
                    "WHERE customerId = %s", customer.getIntId()));
            locationInfo.next();
            conn.createStatement().executeUpdate(String.format(
                    "UPDATE address " +
                    "SET address='%s', address2='%s', cityId=%s,postalCode='%s',phone='%s',lastUpdate=CURDATE(),lastUpdateBy='%s' " +
                    "WHERE addressId=%s",
                    customer.getAddress1(),
                    customer.getAddress2(),
                    locationInfo.getString("cityId"),
                    customer.getZip(),
                    customer.getPhone(),
                    User.getUsername(),
                    locationInfo.getString("addressId")));
            conn.createStatement().executeUpdate(String.format(
                    "UPDATE customer " +
                    "SET customerName='%s', addressId=%s, lastUpdate=CURDATE(), lastUpdateBy='%s' " +
                    "WHERE customerId=%s",
                    customer.getCustomerName(),
                    locationInfo.getString("addressId"),
                    User.getUsername(),
                    customer.getCustomerId()));

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteCustomer(Customer customer) {
        try {
            conn.createStatement().executeUpdate(String.format(
                    "DELETE appointment, customer, address FROM customer " +
                    "INNER JOIN address ON customer.addressId = address.addressId " +
                    "INNER JOIN appointment ON customer.customerId = appointment.customerId " +
                    "WHERE customer.customerId = %s", customer.getCustomerId()));
            customerList.remove(customer);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void addAppointment(String customerId, String userName, String type, String start, String end) {
        try {
            ResultSet userInfo = conn.createStatement().executeQuery(String.format("SELECT userId FROM user WHERE userName = '%s'", userName));
            userInfo.next();
            conn.createStatement().executeUpdate(String.format(
                    "INSERT INTO appointment " +
                    "(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) " +
                    "VALUES (%s,%s,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                    customerId,
                    userInfo.getString("userId"),
                    "not needed",
                    "not needed",
                    "not needed",
                    "not needed",
                    type,
                    "not needed",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    User.getUsername(),
                    User.getUsername()));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            conn.createStatement().executeUpdate(String.format(
                    "UPDATE appointment " +
                    "SET customerId=%s, userId=%s, type='%s', start='%s', end='%s' " +
                    "WHERE appointmentId=%s",
                    appointment.getCustomerId(),
                    appointment.getUserId(),
                    appointment.getType(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    appointment.getIdAsInt()));
        }
        catch (Exception e) {
            System.out.println("Connector.updateAppointment: " + e);
        }
    }

    public static void deleteAppointment(Appointment appointment) {
        try {
            System.out.println(conn.createStatement().executeQuery("DESCRIBE appointment"));
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static ObservableList<String> getCityList() {
        try {
            cityList.removeAll(cityList);
            ResultSet cities = conn.createStatement().executeQuery("SELECT city FROM city");
            while (cities.next()) {
                cityList.add(cities.getString("city"));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return cityList;
    }

    public static ObservableList<String> getUserList() {
        try {
            userList.removeAll(userList);
            ResultSet users = conn.createStatement().executeQuery("SELECT userName FROM user");
            while (users.next()) {
                userList.add(users.getString("userName"));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return userList;
    }

    public static Customer getCustomer(int id) {
        for(Customer customer : customerList) {
            if(customer.getIntId() == id)
                return customer;
        }

        throw new NoSuchElementException();
    }

    public static void refreshCustomerList() {
        try {
            customerList.removeAll(customerList);
            ResultSet customers = conn.createStatement().executeQuery(
                    "SELECT  \n" +
                    "customer.customerId,\n" +
                    "customer.customerName,\n" +
                    "address.address,\n" +
                    "address.address2,\n" +
                    "address.phone,\n" +
                    "address.postalCode,\n" +
                    "city.city,\n" +
                    "country.country\n" +
                    "FROM customer\n" +
                    "INNER JOIN address ON customer.addressId = address.addressId\n" +
                    "INNER JOIN city ON address.cityId = city.cityId\n" +
                    "INNER JOIN country ON city.countryID = country.countryId");
            while(customers.next()) {
                customerList.add(new Customer(customers.getInt("customerId"),
                            customers.getString("customerName"),
                            customers.getString("address"),
                            customers.getString("address2"),
                            customers.getString("phone"),
                            customers.getString("city"),
                            customers.getString("country"),
                            customers.getString("postalCode")));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ObservableList<Customer> getCustomerList() {
        refreshCustomerList();
        return customerList;
    }

    public static void refreshAppointmentList() {
        try {
            appointmentsList.removeAll(appointmentsList);
            ResultSet appointments = conn.createStatement().executeQuery(
                    "SELECT  \n" +
                    "customer.customerName,\n" +
                    "user.userName,\n" +
                    "appointment.appointmentId,\n" +
                    "appointment.customerId,\n" +
                    "appointment.userId,\n" +
                    "appointment.type,\n" +
                    "appointment.start\n" +
                    "FROM customer\n" +
                    "INNER JOIN appointment ON customer.customerId = appointment.customerId\n" +
                    "INNER JOIN user ON user.userId = appointment.userId");
            while(appointments.next()) {
                appointmentsList.add(new Appointment(appointments.getInt("appointmentId"),
                                appointments.getString("customerName"),
                                appointments.getInt("customerId"),
                                appointments.getString("userName"),
                                appointments.getInt("userId"),
                                appointments.getString("type"),
                                "date",
                                "time"));
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static ObservableList<Appointment> getAppointmentList() {
        refreshAppointmentList();
        return appointmentsList;
    }
}
