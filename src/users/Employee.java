package users;

public abstract class Employee extends User {
    public int id, workHourInMonth, presentInWeek, workHourInDay, payPerDay;
    public int absentsInWeek = 0;
    private Boolean suspend = false;

    public Employee(String name, String username, String password, String phone, String email, int id, int workHourInMonth, int workHourInDay, int payPerDay) {
        super(name, username, password, phone, email);
        this.id = id;
        this.workHourInMonth = workHourInMonth;
        this.workHourInDay = workHourInDay;
        this.payPerDay = payPerDay;
    }

    public Boolean getSuspend() {
        return suspend;
    }


    public void setSuspend() {
        this.suspend = !this.suspend;
    }

    public void setAbsentsInWeek(int absentsInWeek) {
        this.absentsInWeek += absentsInWeek;
    }

    public int getSalary() {
        return 30 * workHourInMonth * workHourInDay;
    }

}
