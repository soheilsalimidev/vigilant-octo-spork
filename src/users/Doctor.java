package users;

import models.InterInfo;

public class Doctor extends Employee {
    String bio;
    public String spatial;
    public int visitsCount = 0;

    public Doctor(String name, String username, String password, String phone, String email, int id, int workHourInMonth, int workHourInDay, int payPerDay, String bio, String spatial) {
        super(name, username, password, phone, email, id, workHourInMonth, workHourInDay, payPerDay);
        this.bio = bio;
        this.spatial = spatial;
    }


    @Override
    public String toString() {
        return "doctor info is :" + "\n" +
                "  -name='" + name + '\'' + "\n" +
                "  -spatial='" + spatial + '\'' + "\n" +
                "  -id=" + id;
    }

    public int getSalaryInDay() {
        return visitsCount * payPerDay;
    }

    public int getSalaryInMonth() {
        return visitsCount * payPerDay * 30;
    }


}
