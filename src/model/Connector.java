package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Connector {

    static Connection conn;
    static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    static ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
    static ObservableList<String> cityList = FXCollections.observableArrayList();

    public static void load() {
        try {
            System.out.println("Attempting to connect to DB.....");
            conn = DriverManager.getConnection("jdbc:mysql://3.227.166.251:3306/U07Ebi?profileSQL=true", "U07Ebi", "53689000795");
        }
        catch (Exception e) {
        System.out.println(e);
        }
    }

    public static boolean checkCreds(String username, String password) {
        try {
            ResultSet loginData = conn.createStatement().executeQuery("select userName, userId, password, active from user where userName = '" + username + "'");
            loginData.next();
            if(password.equals(loginData.getString("password")) && loginData.getInt("active") == 1)
                return true;
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public static void addCustomer(String name, String address1, String address2, String city, String zip, String phone) {
        try {
            ResultSet cityInfo = conn.createStatement().executeQuery("SELECT cityId FROM city WHERE city = '" + city + "'");
            cityInfo.next();
            conn.createStatement().executeUpdate("INSERT INTO address " +
                    "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES ('" +
                    address1 + "','" +
                    address2 + "'," +
                    cityInfo.getString("cityId") + ",'" +
                    zip + "','" +
                    phone + "'," +
                    "CURDATE(),'" +
                    User.getUsername() + "'," +
                    "CURDATE(),'" +
                    User.getUsername() + "')");
            ResultSet addressInfo = conn.createStatement().executeQuery("SELECT addressId FROM address WHERE " +
                    "address = '" + address1 + "' " +
                    "AND address2 = '" + address2 + "' " +
                    "AND cityId = '" + cityInfo.getString("cityId") + "' " +
                    "AND postalCode = '" + zip + "'");
            addressInfo.next();
            conn.createStatement().executeUpdate("INSERT INTO customer " +
                    "(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                    "VALUES ('" +
                    name + "'," +
                    addressInfo.getString("addressId") + "," +
                    "1," +
                    "CURDATE(),'" +
                    User.getUsername() + "'," +
                    "CURDATE(),'" +
                    User.getUsername() + "')");

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateCustomer(Customer customer) {
        try {
            ResultSet cityInfo = conn.createStatement().executeQuery("SELECT cityId FROM city WHERE city = '" + customer.getCity() + "'");
            cityInfo.next();
            ResultSet addressInfo = conn.createStatement().executeQuery("SELECT addressId FROM address WHERE " +
                    "address = '" + customer.getAddress1() + "' " +
                    "AND address2 = '" + customer.getAddress2() + "' " +
                    "AND cityId = '" + cityInfo.getString("cityId") + "' " +
                    "AND postalCode = '" + customer.getZip() + "'");
            addressInfo.next();
            System.out.println();
            conn.createStatement().executeUpdate("UPDATE address " +
                    "SET " +
                    "address='" + customer.getAddress1() +
                    "', address2='" + customer.getAddress2() +
                    "', cityId=" + cityInfo.getString("cityId") +
                    ", postalCode='" + customer.getZip() +
                    "', phone='" + customer.getPhone() +
                    "', lastUpdate=CURDATE()" +
                    ", lastUpdateBy='" +User.getUsername() +
                    "' WHERE addressId=" +
                    addressInfo.getString("addressId"));
            conn.createStatement().executeUpdate("UPDATE customer " +
                    "SET " +
                    "customerName='" + customer.getCustomerName() +
                    "', addressId=" + addressInfo.getString("addressId") +
                    ", lastUpdate=CURDATE()" +
                    ", lastUpdateBy='" + User.getUsername() +
                    "' WHERE customerId=" + customer.getCustomerId());

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteCustomer(Customer customer) {
//        try {
//            ResultSet ids = conn.createStatement().executeQuery("SELECT  \n" +
//                    "customer.customerId,\n" +
//                    "address.addressId,\n" +
//                    "address.cityId,\n" +
//                    "city.countryId,\n" +
//                    "appointment.appointmentId\n" +
//                    "FROM customer\n" +
//                    "INNER JOIN address ON customer.addressId = address.addressId\n" +
//                    "INNER JOIN city ON address.cityId = city.cityId\n" +
//                    "INNER JOIN country ON city.countryID = country.countryId\n" +
//                    "INNER JOIN appointment ON customer.customerId = appointment.customerId\n" +
//                    "WHERE customer.customerId = 1");
//            customerList.remove(customer);
//        }
//        catch(Exception e) {
//            System.out.println(e);
//        }
    }

    public static void addAppointment(Appointment appointment) {

    }

    public static void updateAppointment(Appointment appointment) {

    }

    public static void deleteAppointment(Appointment appointment) {

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

    public static ObservableList<Customer> getCustomerList() {
        try {
            customerList.removeAll(customerList);
            ResultSet customers = conn.createStatement().executeQuery("SELECT  \n" +
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

        return customerList;
    }

    public ObservableList<Appointment> getAppointmentList() {
        appointmentsList.removeAll(appointmentsList);
        return appointmentsList;
    }
}
