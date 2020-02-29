package model;

public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;

    public Appointment(int id, String title, String description, String location, String contact, String type, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
    }

    public String getId() { return Integer.toString(id); }

    public int getIdAsInt() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public String getContact() { return contact; }

    public String getType() { return type; }

    public String getUrl() { return url; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setLocation(String location) { this.location = location; }

    public void setContact(String contact) { this.contact = contact; }

    public void setType(String type) { this.type = type; }

    public void setUrl(String url) { this.url = url; }
}
