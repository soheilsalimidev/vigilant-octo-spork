package users;

import models.InterInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Guards extends Employee {

    private final ArrayList<InterInfo> interInfos = new ArrayList<>();

    public Guards(String name, String username, String password, String phone, String email, int id, int workHourInMonth, int workHourInDay, int payPerDay) {
        super(name, username, password, phone, email, id, workHourInMonth, workHourInDay, payPerDay);
    }

    public void newInter(InterInfo interInfo) {
        interInfos.add(interInfo);
    }

    public InterInfo getInter(String username) {
        return interInfos.stream().filter(interInfo -> interInfo.username == username).findFirst().orElse(new InterInfo(username, new Date()));
    }

    public ArrayList<InterInfo> getInterAll(String username) {
        return interInfos.stream().filter(interInfo -> interInfo.username == username).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getInters() {
        return interInfos.toString();
    }

}
