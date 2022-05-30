import models.InterInfo;
import models.Medicine;
import models.Prescription;
import models.Queue;
import users.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class HospitalController {
    private final ArrayList<Doctor> doctors = new ArrayList<>();
    private final Guards guards = new Guards("system guard", "", "", "", "", 0, 0, 0, 0);
    private final ArrayList<Servant> servants = new ArrayList<>();
    private final ArrayList<Medicine> medicines = new ArrayList<>();
    private final ArrayList<Secondary> secondaries = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    int selectedUserIndex;
    int selectedPatientsIndex;
    int selectedUserId;

    int addDates = 0;
    Date today = new Date();

    public void beforeLogin(String username) {
        guards.newInter(new InterInfo(username, new Date(new Date().getTime() + (long) addDates * 24 * 60 * 60 * 1000)));
    }

    public void beforeLoginOut(String username, String role) {
        InterInfo user = guards.getInter(username);
        user.setLoginOutDate(new Date(new Date().getTime() + (long) addDates * 24 * 60 * 60 * 1000));
        Employee employee;
        switch (role) {
            case "doctor":
                employee = doctors.stream().findFirst().filter(doctor -> doctor.username.equals(username)).get();
                break;
            case "sec":
                employee = secondaries.stream().findFirst().filter(doctor -> doctor.username.equals(username)).get();
                break;
            case "ser":
                employee = servants.stream().findFirst().filter(doctor -> doctor.username.equals(username)).get();
            default:
                return;
        }
        guards.setAbsentsInWeek(TimeUnit.DAYS.convert(user.loginOutDate.getTime() - user.loginDate.getTime(), TimeUnit.MILLISECONDS) < employee.workHourInDay ? 1 : 0);
    }

    public String getGuardSummary() {
        return guards.getInters();
    }

    // getter
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    //models.Medicine controller
    public void addMedicine(String medicineName, int medicinePrice, LocalDate medicineAddDate, LocalDate medicineExpirationDate) {
        medicines.add(new Medicine(medicineName, medicinePrice, medicineAddDate, medicineExpirationDate));
    }

    public boolean checkMedicineExists(String name) {
        return medicines.stream().anyMatch(medicine -> medicine.name.equals(name));
    }

    public Medicine getMedicineByName(String name) {
        return medicines.stream().filter(medicine -> medicine.name.equals(name)).findFirst().get();
    }

    // doctors controller
    public void addSecondary(String name, String username, String password, String phone, String email, int id, int workHourInMonth, int workHourInDay, int payPerDay) {
        secondaries.add(new Secondary(name, username, password, phone, email, id, workHourInMonth, workHourInDay, payPerDay, doctors.get(selectedUserIndex).id));
    }

    public boolean doctorExist(String name, String password) {
        return doctors.stream().anyMatch(doctor -> doctor.login(name, password));
    }

    public void doctorLogin(String name, String password) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).login(name, password)) {
                selectedUserIndex = i;
                selectedUserId = doctors.get(i).id;
                break;
            }
        }
    }

    public void addDoctor(Doctor doc) {
        doctors.add(doc);
    }

    public String findDoctors(String speciality) {
        final String[] text = {""};
        if (doctors.stream().anyMatch(doctor -> doctor.spatial.equals(speciality))) {
            doctors.stream().filter(doctor -> doctor.spatial.equals(speciality)).forEach(doctor -> {
                text[0] += "\n" + (doctor.id + " ) is " + doctor.name + " with speciality at " + doctor.spatial);
            });
        } else text[0] = "no doctor found :(";
        return text[0];
    }

    public int getDocSalaryInDay() {
        return doctors.get(selectedUserIndex).getSalaryInDay();
    }

    public int getDocSalaryInMonth() {
        return doctors.get(selectedUserIndex).getSalaryInMonth();
    }
    // patient controller

    public boolean patientExist(String name, String password) {
        return patients.stream().anyMatch(patient -> patient.login(name, password));
    }

    public void patientLogin(String name, String password) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).login(name, password)) {
                selectedUserIndex = i;
                break;
            }
        }
    }

    public void patientRegister(String name, String password, String phoneNumber, String email) {
        patients.add(new Patient(name, name, password, phoneNumber, email, patients.size() + 1));
    }

    public void addPrescription(Patient patient, ArrayList<Medicine> medicinesForPrescription) {
        if (secondaries.stream().findFirst().filter(secondary -> secondary.doctorId == doctors.get(selectedUserIndex).id).isPresent()) {
            ArrayList<Queue> queuesDays = secondaries.stream().findFirst().filter(secondary -> secondary.doctorId == doctors.get(selectedUserIndex).id).get().queuesDays;
            patient.prescriptions.add(new Prescription(1, doctors.get(selectedUserIndex).id, patient.id, medicinesForPrescription, today));
            doctors.get(selectedUserIndex).visitsCount++;
            for (int i = 0; i < queuesDays.size(); i++) {
                if (queuesDays.get(i).patientId == patient.id) {
                    queuesDays.remove(i);
                    break;
                }
            }
        }
    }

    public Patient findNextPatient() {
        if (secondaries.stream().findFirst().filter(secondary -> secondary.doctorId == selectedUserId).isPresent()) {
            ArrayList<Queue> queuesDays = secondaries.stream().findFirst().filter(secondary -> secondary.doctorId == selectedUserId).get().queuesDays;
            if (queuesDays.stream().anyMatch(queue -> queue.doctorId == doctors.get(selectedUserIndex).id && utility.isSameDay(queue.date, today))) {
                int patientId = queuesDays.stream().filter(queue -> queue.doctorId == doctors.get(selectedUserIndex).id && utility.isSameDay(queue.date, today)).findFirst().get().patientId;
                return patients.stream().filter(patient -> patientId == patient.id).findFirst().get();
            }
        }
        return null;
    }

    public void changeInfo(String name, String password, String phone, String email) {
        patients.get(selectedPatientsIndex).updateInfo(name, password, phone, email);
    }

    public int makeAppointment(String docId, Date date, boolean em) {
        if (isSameDay(dateToCalendar(date), dateToCalendar(today)) || dateToCalendar(date).after(dateToCalendar(today))) {
            if (secondaries.stream().findFirst().filter(secondary -> secondary.doctorId == Integer.parseInt(docId)).isPresent()) {
                ArrayList<Queue> queuesDays = secondaries.stream().findFirst().filter(secondary -> secondary.doctorId == Integer.parseInt(docId)).get().queuesDays;
                Queue appointment = new Queue(queuesDays.size() + 1, date, patients.get(selectedPatientsIndex).id, Integer.parseInt(docId), em);
                queuesDays.add(appointment);
                patients.get(selectedPatientsIndex).queues.add(appointment);
                return queuesDays.size();
            }
        }
        return 0;
    }

    public String appointmentHistory() {
        return (utility.listToString(patients.get(selectedPatientsIndex).queues));
    }

    public String prescriptionHistory() {
        return (utility.listToString(patients.get(selectedPatientsIndex).prescriptions));
    }

    public void goForward(int day) {
        addDates = day;
        today = new Date(today.getTime() + (long) day * 24 * 60 * 60 * 1000);
    }

    //secondaries

    public boolean secExist(String name, String password) {
        return secondaries.stream().anyMatch(secondaries -> secondaries.login(name, password));
    }

    public void secLogin(String name, String password) {
        for (int i = 0; i < secondaries.size(); i++) {
            if (secondaries.get(i).login(name, password)) {
                selectedUserIndex = i;
                break;
            }
        }
    }

    public void sortAppointments() {
        Collections.sort(secondaries.get(selectedUserIndex).queuesDays);
    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    //Servant

    public boolean servantExist(String name, String password) {
        return servants.stream().anyMatch(doctor -> doctor.login(name, password));
    }

    public void servantLogin(String name, String password) {
        for (int i = 0; i < servants.size(); i++) {
            if (servants.get(i).login(name, password)) {
                selectedUserIndex = i;
                selectedUserId = servants.get(i).id;
                break;
            }
        }
    }

    public void addServant(Servant doc) {
        servants.add(doc);
    }

    public void addProblem(String problem) {
        servants.get(selectedUserIndex).addProblem(problem);
    }

    public void addRequest(String request) {
        servants.get(selectedUserIndex).addRequest(request);
    }

    public String getProblemsAndRequests() {
        return servants.toString();
    }

    public int getEmSalary(String username, String role) {
        Employee employee;
        switch (role) {
            case "sec":
                employee = secondaries.stream().findFirst().filter(secondaries -> secondaries.username.equals(username)).get();
                break;
            case "ser":
                employee = servants.stream().findFirst().filter(servant -> servant.username.equals(username)).get();
            default:
                return 0;
        }
        return employee.getSalary();
    }

    public String getInnerLogins(String username) {
        List<InterInfo> list = guards.getInterAll(username);

        return guards.getInterAll(username).toString();
    }

    public String getAllEmployees() {
        return "doctors : \n" + doctors + '\n' + "servants : \n" + servants + '\n' + "secondaries : \n" + secondaries;
    }

    public void suspend(String username) {
        if (doctors.stream().anyMatch(doctor -> Objects.equals(doctor.username, username))) {
            doctors.stream().filter(doctor -> doctor.username == username).findFirst().get().setSuspend();
            return;
        }
        if (secondaries.stream().anyMatch(doctor -> Objects.equals(doctor.username, username))) {
            secondaries.stream().filter(doctor -> doctor.username == username).findFirst().get().setSuspend();
            return;
        }
        if (servants.stream().anyMatch(doctor -> Objects.equals(doctor.username, username))) {
            servants.stream().filter(doctor -> doctor.username == username).findFirst().get().setSuspend();
            return;
        }
    }

}
