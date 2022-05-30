package users;

public class Admin {
    static String name = "admin";
    static String password = "admin";

    public static boolean login(String name, String password) {
        return (Admin.name.equals(name) && Admin.password.equals(password));
    }
}
