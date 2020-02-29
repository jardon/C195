package model;

public class Appointment {

    private int id;
    private String customer;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String date;
    private String time;

    public Appointment(int id, String customer, String title, String description, String location, String contact, String type, String url, String date, String time) {
        this.id = id;
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.date = date;
        this.time = time;
    }

    public String getId() { return Integer.toString(id); }

    public String getCustomer() { return customer; }

    public int getIdAsInt() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public String getContact() { return contact; }

    public String getType() { return type; }

    public String getUrl() { return url; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public void setId(int id) { this.id = id; }

    public void setCustomer(String customer) { this.customer = customer; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setLocation(String location) { this.location = location; }

    public void setContact(String contact) { this.contact = contact; }

    public void setType(String type) { this.type = type; }

    public void setUrl(String url) { this.url = url; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }
}
