package users;

import models.InterInfo;

import java.util.ArrayList;

public class Servant extends Employee {
    private final ArrayList<String> problems = new ArrayList<>();
    private final ArrayList<String> requests = new ArrayList<>();

    public Servant(String name, String username, String password, String phone, String email, int id, int workHourInMonth, int workHourInDay, int payPerDay) {
        super(name, username, password, phone, email, id, workHourInMonth, workHourInDay, payPerDay);
    }

    public void addProblem(String problem) {
        problems.add(problem);
    }

    public void addRequest(String requests) {
        problems.add(requests);
    }

    @Override
    public String toString() {
        return "\nServant :" +
                "\n, username='" + username + '\'' +
                "\n, name='" + name + '\'' +
                "\n, problems=" + problems +
                "\n, requests=" + requests;
    }
}
