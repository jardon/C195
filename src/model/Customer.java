package model;

public class Customer {

    private String name;
    private String address;
    private String address2;
    private String city;
    private String country;
    private String zip;
    private String phone;

    public Customer(String name, String address, String address2, String city, String country, String zip, String phone) {
        this.name = name;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
    }

    public String getName() { return name; }

    public String getAddress() { return  address; }

    public String getAddress2() { return  address2; }

    public String getCity() { return city; }

    public String getCountry() { return country; }

    public String getZip() { return  zip; }

    public String getPhone() { return phone; }

    public void setName(String name) { this.name = name; }

    public void setAddress(String address) { this.address = address; }

    public void setAddress2(String address2) { this.address2 = address2; }

    public void setCity(String city) { this.city = city; }

    public void setCountry(String country) { this.country = country; }

    public void setZip(String zip) { this.zip = zip; }

    public void setPhone(String phone) { this.phone = phone; }
}
