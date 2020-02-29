package model;

public class User {

    private static String username;
    private static int uid;
    private static boolean isActive;

    public static void login(String userName, int userId, boolean active) {
        username = userName;
        uid = userId;
        isActive = active;
    }

    public static void logout() {
        username = null;
        uid = -1;
        isActive = false;
    }

    public static String getUsername() { return username; }

    public static int getUserId() { return uid; }
}
