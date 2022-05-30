package models;

import java.util.ArrayList;
import java.util.Date;


public class Prescription {
    int id, doctorId, patientId;
    ArrayList<Medicine> medicine;
    Date addedDate;

    public Prescription(int id, int doctorId, int patientId, ArrayList<Medicine> medicine, Date addedDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.medicine = medicine;
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "models.Prescription info is :\n" +
                "  " + "  -id=" + id + "\n" +
                "  " + "  -doctorId=" + doctorId + "\n" +
                "  " + "  -patientId=" + patientId + "\n" +
                "  " + "  -medicine=" + medicine.toString() + "\n" +
                "  " + "  -addedDate=" + addedDate;
    }
}
