import models.Medicine;
import users.Admin;
import users.Doctor;
import users.Patient;
import users.Servant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Hospital {

    static HospitalController hospitalController = new HospitalController();
    static String username;

    public static void main(String[] args) {
        login();
    }

    public static void login() {
        Scanner in = new Scanner(System.in);
        System.out.println("if you dont have account inter info its automatically register you :)");
        System.out.println("Inter your username: ");
        String name = in.next();
        System.out.println("Inter your password: ");
        String password = in.next();

        if (Admin.login(name, password)) {
            utility.cls();
            adminActions();
        } else if (hospitalController.doctorExist(name, password)) {
            hospitalController.doctorLogin(name, password);
            utility.cls();
            hospitalController.beforeLogin(name);
            username = name;
            doctorActions();
        } else if (hospitalController.secExist(name, password)) {
            hospitalController.secLogin(name, password);
            utility.cls();
            hospitalController.beforeLogin(name);
            username = name;
            secActions();

        } else if (hospitalController.servantExist(name, password)) {
            hospitalController.servantLogin(name, password);
            utility.cls();
            hospitalController.beforeLogin(name);
            username = name;
            servantActions();
        } else {
            if (hospitalController.patientExist(name, password)) {
                hospitalController.patientLogin(name, password);
            } else {
                System.out.println("inter your phone number: ");
                String phone = in.next();
                System.out.println("inter your email: ");
                String email = in.next();
                hospitalController.patientRegister(name, password, phone, email);
            }
            utility.cls();
            patientActions();
        }

    }

    //servant actions
    public static void servantActions() {
        Scanner in = new Scanner(System.in);
        System.out.println("1) add new problem");
        System.out.println("2) add new request");
        System.out.println("3) see salary");
        System.out.println("4) worked days");
        System.out.println("5) exit");
        String nextCommand = in.next();

        switch (nextCommand) {
            case "1":
                System.out.println("Inter the problem:");
                hospitalController.addProblem(in.next());
                break;
            case "2":
                System.out.println("Inter the request:");
                hospitalController.addRequest(in.next());
                break;
            case "3":
                System.out.println(hospitalController.getEmSalary(username, "ser"));
                break;
            case "4":
                System.out.println(hospitalController.getInnerLogins(username));
                break;
            case "5":
                utility.cls();
                System.out.println("good bye");
                hospitalController.beforeLoginOut(username, "ser");
                login();
                return;
        }
        servantActions();
    }

    // admin actions
    public static void adminActions() {
        Scanner in = new Scanner(System.in);
        System.out.println("1) add new doctor");
        System.out.println("2) add new medicine");
        System.out.println("3) add change day");
        System.out.println("4) see guards summary");
        System.out.println("5) add new servant");
        System.out.println("6) see problems and requests");
        System.out.println("7) suspend or unsuspend a employee");
        System.out.println("8) exit");
        String nextCommand = in.next();

        switch (nextCommand) {
            case "1":
                addNewDoctor();
                break;
            case "2":
                addNewMedicine();
                break;
            case "3":
                System.out.println("how many days should go forward (back with minus) ? ");
                hospitalController.goForward(Integer.parseInt(in.next()));
                break;
            case "4":
                System.out.println(hospitalController.getGuardSummary());
                break;
            case "5":
                addNewServant();
                break;
            case "6":
                System.out.println(hospitalController.getProblemsAndRequests());
                break;
            case "7":
                System.out.println(hospitalController.getAllEmployees());
                System.out.println("inter employee user name ?");
                String username = in.next();
                hospitalController.suspend(username);
                System.out.println("action is done");
                break;
            case "8":
                utility.cls();
                System.out.println("good bye");
                login();
                return;
        }
        adminActions();
    }

    static void addNewDoctor() {
        Scanner in = new Scanner(System.in);
        utility.cls();

        System.out.println("Inter doctor Id:");
        String doctorId = in.next();
        System.out.println("Inter doctor name:");
        String doctorName = in.next();
        System.out.println("Inter doctor password:");
        String doctorPass = in.next();
        System.out.println("Inter doctor spatial:");
        String doctorSpatial = in.next();
        System.out.println("Inter doctor bio:");
        String bio = in.next();
        System.out.println("Inter doctor  work hours in a month:");
        int workHourInMonth = in.nextInt();
        System.out.println("Inter doctor work hours in a day:");
        int workHourInDay = in.nextInt();
        System.out.println("Inter doctor pay per hour:");
        int payPerDay = in.nextInt();
        System.out.println("Inter doctor phone number:");
        String phone = in.next();
        System.out.println("Inter doctor email:");
        String email = in.next();
        hospitalController.addDoctor(new Doctor(doctorName, doctorName, doctorPass, phone, email, Integer.parseInt(doctorId),
                workHourInMonth, workHourInDay, payPerDay, bio, doctorSpatial));
        utility.cls();
        System.out.println("doctor added :)");
    }

    static void addNewServant() {
        Scanner in = new Scanner(System.in);
        utility.cls();

        System.out.println("Inter servant Id:");
        String doctorId = in.next();
        System.out.println("Inter servant name:");
        String doctorName = in.next();
        System.out.println("Inter servant password:");
        String doctorPass = in.next();
        System.out.println("Inter servant  work hours in a month:");
        int workHourInMonth = in.nextInt();
        System.out.println("Inter servant work hours in a day:");
        int workHourInDay = in.nextInt();
        System.out.println("Inter servant pay per hour:");
        int payPerDay = in.nextInt();
        System.out.println("Inter servant phone number:");
        String phone = in.next();
        System.out.println("Inter servant email:");
        String email = in.next();
        hospitalController.addServant(new Servant(doctorName, doctorName, doctorPass, phone, email, Integer.parseInt(doctorId), workHourInMonth, workHourInDay, payPerDay));
        utility.cls();
        System.out.println("servant added :)");
    }

    static void addNewMedicine() {
        Scanner in = new Scanner(System.in);
        utility.cls();
        System.out.println("Inter medicine name:");
        String medicineName = in.next();
        System.out.println("Inter medicine price:");
        int medicinePrice = Integer.parseInt(in.next());
        System.out.println("Inter medicine create date:");
        LocalDate medicineAddDate = utility.stringToDate(in.next());
        System.out.println("Inter medicine expiration date:");
        LocalDate medicineExpirationDate = utility.stringToDate(in.next());
        hospitalController.addMedicine(medicineName, medicinePrice, medicineAddDate, medicineExpirationDate);
        utility.cls();
        System.out.println("doctor added");
    }

    // doctor actions
    public static void doctorActions() {
        Scanner in = new Scanner(System.in);
        System.out.println("1) add new secondary");
        System.out.println("2) see patient");
        System.out.println("3) add prescription for correct patient");
        System.out.println("4) see all medicine");
        System.out.println("5) see today's income");
        System.out.println("6) see this month income(predict base on today's income)");
        System.out.println("7) exit");

        String nextCommand = in.next();
        switch (nextCommand) {
            case "1":
                addNewSec();
                break;
            case "2":
                utility.cls();
                Patient patient = hospitalController.findNextPatient();
                if (patient != null)
                    System.out.println(patient);
                else
                    System.out.println("no patient");
                break;
            case "3":
                addPrescription();
                break;
            case "4":
                utility.cls();
                System.out.println(hospitalController.getMedicines().toString());
                break;
            case "5":
                System.out.println(hospitalController.getDocSalaryInDay());
                break;
            case "6":
                System.out.println(hospitalController.getDocSalaryInMonth());
                break;
            case "7":
                utility.cls();
                hospitalController.beforeLoginOut(username, "doctor");
                System.out.println("good bye");
                login();
                return;

        }
        doctorActions();
    }

    static void addNewSec() {
        utility.cls();
        Scanner in = new Scanner(System.in);

        System.out.println("Inter secondary name:");
        String secondaryName = in.next();
        System.out.println("Inter secondary password:");
        String password = in.next();
        System.out.println("Inter secondary ID:");
        int secondaryId = Integer.parseInt(in.next());
        System.out.println("Inter secondary  work hours in a month:");
        int workHourInMonth = in.nextInt();
        System.out.println("Inter secondary work hours in a day:");
        int workHourInDay = in.nextInt();
        System.out.println("Inter secondary pay per hour:");
        int payPerDay = in.nextInt();
        System.out.println("Inter secondary phone number:");
        String phone = in.next();
        System.out.println("Inter secondary email:");
        String email = in.next();
        hospitalController.addSecondary(secondaryName, secondaryName, password, phone, email, secondaryId, workHourInMonth, workHourInDay, payPerDay);
        utility.cls();
        System.out.println("secondary added");
    }

    static void addPrescription() {
        Scanner in = new Scanner(System.in);
        utility.cls();
        ArrayList<Medicine> medicinesForPrescription = new ArrayList<>();
        Patient patientE = hospitalController.findNextPatient();
        if (patientE != null) {
            boolean continueAdding;
            do {
                System.out.println(hospitalController.getMedicines().toString());
                System.out.println("inter medicine name :");
                String medicineName = in.next();
                if (hospitalController.checkMedicineExists(medicineName))
                    medicinesForPrescription.add(hospitalController.getMedicineByName(medicineName));
                else
                    System.out.println("no such medicine");
                System.out.println("Do you want to continue(y/n) ?");
                continueAdding = in.next().equals("y");
                utility.cls();
            } while (continueAdding);
            hospitalController.addPrescription(patientE, medicinesForPrescription);
            System.out.println("Prescription added.patient removed from the queues :)");
        } else
            System.out.println("no patient");
    }

    //secActions
    public static void secActions() {
        Scanner in = new Scanner(System.in);
        System.out.println("1) sort the appointments");
        System.out.println("2) see salary");
        System.out.println("3) worked days");
        System.out.println("4) exit");
        String nextCommand = in.next();
        switch (nextCommand) {
            case "1":
                utility.cls();
                hospitalController.sortAppointments();
                System.out.println("appointments is been sort");
                break;
            case "2":
                System.out.println(hospitalController.getEmSalary(username, "sec"));
                break;
            case "3":
                System.out.println(hospitalController.getInnerLogins(username));
                break;
            case "4":
                utility.cls();
                hospitalController.beforeLoginOut(username, "sec");
                System.out.println("good bye");
                login();
                return;

        }
        secActions();
    }

    // patient actions
    public static void patientActions() {
        Scanner in = new Scanner(System.in);
        System.out.println("1) change info");
        System.out.println("2) doctors lists");
        System.out.println("3) search for doctor");
        System.out.println("4) make appointment with doctor");
        System.out.println("5) history of appointments");
        System.out.println("6) history of prescription");
        System.out.println("7) exit");

        String nextCommand = in.next();
        switch (nextCommand) {
            case "1":
                changeInfo();
                break;
            case "2":
                utility.cls();
                for (Doctor doctor : hospitalController.getDoctors())
                    System.out.println(doctor.id + " ) is " + doctor.name + " with speciality at " + doctor.spatial);
                break;
            case "3":
                utility.cls();
                System.out.println("Inter the speciality you looking for: ");
                String speciality = in.next();
                System.out.println(hospitalController.findDoctors(speciality));
                break;
            case "4":
                addNewAppointment();
                break;
            case "5":
                utility.cls();
                System.out.println(hospitalController.appointmentHistory());
                break;
            case "6":
                utility.cls();
                System.out.println(hospitalController.prescriptionHistory());
                break;
            case "7":
                utility.cls();
                System.out.println("good bye");
                login();
                return;
        }
        patientActions();
    }

    private static void addNewAppointment() {
        Scanner in = new Scanner(System.in);
        utility.cls();
        System.out.println("Inter id of doctor you want to make appointment with: ");
        String docId = in.next();
        System.out.println("is it emergency ?");
        String emergencyInput = in.next();
        boolean isEm = Objects.equals(emergencyInput, "yes") || emergencyInput.equals("y");
        System.out.println("Inter date of this appointment: ");
        Date date = Date.from(utility.stringToDate(in.next()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        int id = hospitalController.makeAppointment(docId, date, isEm);
        if (id != 0)
            System.out.println("appointment added :) your appointment ID is :" + id);
        else
            System.out.println("interr valid date");

        utility.cls();
    }

    private static void changeInfo() {
        Scanner in = new Scanner(System.in);
        utility.cls();
        System.out.println("Inter your name: ");
        String name = in.next();
        System.out.println("Inter your password: ");
        String password = in.next();
        System.out.println("Inter your phone number: ");
        String phone = in.next();
        System.out.println("Inter your email: ");
        String email = in.next();
        hospitalController.changeInfo(name, password, phone, email);
        utility.cls();
        System.out.println("data saves successfully :)");
    }
}
