package models;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class InterInfo {
    public String username;
    public Date loginDate;
    public Date loginOutDate;

    public InterInfo(String username, Date date) {
        this.username = username;
        this.loginDate = date;
    }

    public void setLoginOutDate(Date loginOutDate) {
        this.loginOutDate = loginOutDate;
    }

    @Override
    public String toString() {
        return "InterInfo\n =" +
                "username='" + username + '\'' +
                "\n loginDate=" + loginDate +
                "\n loginOutDate=" + (loginOutDate != null ? loginOutDate : "") +
                "\n the difference is " + (loginOutDate != null ? TimeUnit.HOURS.convert(loginOutDate.getTime() - loginDate.getTime(), TimeUnit.MILLISECONDS) + "hour" : "");
    }
}
