package model;

public class Appointment {

    private int id;
    private String customer;
    private int customerId;
    private int userId;
    private String type;
    private String date;
    private String time;
    private String userName;

    public Appointment(int id, String customer, int customerId, String userName, int userId, String type, String date, String time) {
        this.id = id;
        this.customer = customer;
        this.customerId = customerId;
        this.userName = userName;
        this.userId = userId;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public String getId() { return Integer.toString(id); }

    public String getCustomer() { return customer; }

    public int getCustomerId() { return customerId; }

    public int getUserId() { return userId; }

    public String getUserName() { return userName; }

    public int getIdAsInt() { return id; }

    public String getType() { return type; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public void setId(int id) { this.id = id; }

    public void setUserId(int userId) { this.userId = userId; }

    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public void setCustomer(String customer) { this.customer = customer; }

    public void setType(String type) { this.type = type; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }
}
