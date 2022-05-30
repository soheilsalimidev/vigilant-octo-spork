package users;

import models.InterInfo;
import models.Queue;
import java.util.ArrayList;


public class Secondary extends Employee {
    public int doctorId;
    public ArrayList<Queue> queuesDays = new ArrayList<>();

    public Secondary(String name, String username, String password, String phone, String email, int id, int workHourInMonth, int workHourInDay, int payPerDay, int doctorId) {
        super(name, username, password, phone, email, id, workHourInMonth, workHourInDay, payPerDay);
        this.doctorId = doctorId;
    }
}
