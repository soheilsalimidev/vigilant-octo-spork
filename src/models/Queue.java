package models;

import java.util.Date;


public class Queue implements Comparable<Queue> {
    int id;
    public Date date;
    public int patientId;
    public int doctorId;
    boolean isImportant;

    public Queue(int id, Date date, int patientId, int doctorId) {
        this.id = id;
        this.date = date;
        this.patientId = patientId;
        this.doctorId = doctorId;
        isImportant = false;
    }

    public Queue(int id, Date date, int patientId, int doctorId, boolean isImportant) {
        this.id = id;
        this.date = date;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.isImportant = isImportant;
    }

    @Override
    public String toString() {
        return "models.Queue info is \n" +
                "  " + "  -id=" + id + "\n" +
                "  " + "  -date=" + date + "\n" +
                "  " + "  -patientId=" + patientId + "\n" +
                "  " + "  -doctorId=" + doctorId;
    }


    @Override
    public int compareTo(Queue queue) {
        if (isImportant) return 5;
        else if (!queue.date.equals(date)) {
            return queue.date.compareTo(date);
        } else return id > queue.id ? 0 : 1;
    }
}
