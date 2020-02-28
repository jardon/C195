package model;

public class Customer {

    private int customerId;
    private String customerName;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String phone;
    private String zip;

    public Customer(int customerId, String customerName, String address1, String address2, String phone, String city, String country,  String zip){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        setCountry();
        this.phone = phone;
        this.zip = zip;
    }

    public String getCustomerId() { return Integer.toString(customerId); }

    public int getIntId() { return customerId; }

    public String getCustomerName() { return customerName; }

    public String getAddress1() { return address1; }

    public String getAddress2() { return address2; }

    public String getAddress() { return address1 + " " + address2 + ", " + city + " " + country + " " + zip; }

    public String getCity() { return city; }

    public String getPhone() { return phone; }

    public String getZip() { return  zip; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public void setAddress1(String address1) { this.address1 = address1; }

    public void setAddress2(String address2) { this.address2 = address2; }

    public void setCity(String city) {
        this.city = city;
        setCountry();
    }

//    public void setCountry(String city) { this.country = country; }

    public void setZip(String zip) { this.zip = zip; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setCountry() {
        switch(city) {
            case("New York"):
                country = "US";
                break;
            case("Los Angeles"):
                country = "US";
                break;
            case("Toronto"):
                country = "Canada";
                break;
            case("Vancouver"):
                country = "Canada";
                break;
            case("Oslo"):
                country = "Norway";
                break;
            default:
                System.out.println("Could not find valid country;");
        }
    }
}
