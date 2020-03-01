package model;

public class Appointment {

    private int id;
    private String customer;
    private int customerId;
    private int userId;
    private String type;
    private String userName;
    private String start;
    private String end;

    public Appointment(int id, String customer, int customerId, String userName, int userId, String type, String start, String end) {
        this.id = id;
        this.customer = customer;
        this.customerId = customerId;
        this.userName = userName;
        this.userId = userId;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public String getId() { return Integer.toString(id); }

    public String getCustomer() { return customer; }

    public int getCustomerId() { return customerId; }

    public int getUserId() { return userId; }

    public String getUserName() { return userName; }

    public int getIdAsInt() { return id; }

    public String getType() { return type; }

    public String getStart() { return start; }

    public String getEnd() { return end; }

    public void setId(int id) { this.id = id; }

    public void setUserId(int userId) { this.userId = userId; }

    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public void setCustomer(String customer) { this.customer = customer; }

    public void setType(String type) { this.type = type; }

    public void setStart(String start) { this.start = start; }

    public void setEnd(String end) { this.end = end; }
}
