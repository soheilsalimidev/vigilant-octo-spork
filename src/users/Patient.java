package users;

import models.Prescription;
import models.Queue;
import users.User;

import java.util.ArrayList;


public class Patient extends User {
    public int id;
    public ArrayList<Prescription> prescriptions = new ArrayList<>();
    public ArrayList<Queue> queues = new ArrayList<>();

    public Patient(String name, String username, String password, String phone, String email, int id) {
        super(name, username, password, phone, email);
        this.id = id;
    }

    @Override
    public String toString() {
        return "users.Patient info is \n" + "\n" +
                "  -name='" + name + '\'' + "\n" +
                "  -phoneNumber='" + phone + '\'' + "\n" +
                "  -id=" + id + "\n" +
                "  -prescriptions=" + prescriptions + "\n" +
                "  -queues=" + queues;
    }

    public void updateInfo(String name, String password, String phoneNumber, String email) {
        this.name = name;
        this.password = password;
        phone = phoneNumber;
        this.email = email;
    }

}
