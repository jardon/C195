package model;

public class User {

    private static String username;
//    private static String uid;
    private static boolean isActive;

    public static void login(String uid, boolean active) {
        username = uid;
//        uid = uid;
        isActive = active;
    }

    public static void logout() {
        username = null;
//        uid = null;
        isActive = false;
    }

    public static String getUsername() { return username; }
}
