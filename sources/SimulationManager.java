package App;
import javax.swing.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SimulationManager {
    // attributes
    private int currentTime;
    private final int simulationTime; // Total simulation time in seconds
    private final double patientsPerSecond;
    private final ResourcePool resources;
    private final JTextArea logArea;
    private final Queue<Patient> waitingQueue;
    private final Queue<Patient> inTreatmentQueue;
    private final Random random;

    // attributes for summary report
    private int totalPatients;
    private int totalWaitingTime;
    private int totalTreatmentTime;

    // ctor
    public SimulationManager(int simulationTime, double patientsPerSecond, ResourcePool resources, JTextArea logArea) {
        this.currentTime = 0;
        this.simulationTime = simulationTime;
        this.patientsPerSecond = patientsPerSecond;
        this.resources = resources;
        this.logArea = logArea;
        this.waitingQueue = new LinkedList<>();
        this.inTreatmentQueue = new LinkedList<>();
        this.random = new Random();
        this.totalPatients = 0;
        this.totalWaitingTime = 0;
        this.totalTreatmentTime = 0;
    }

    // Log events
    public void logEvent(String message) {
        String timeStamp = String.format("Time: %d day - ", currentTime);
        String eventLog = timeStamp + message;
        logArea.append(eventLog + "\n");
        System.out.println(eventLog);
    }

    // Generate Weibull-distributed random number
    private double generateWeibull(double shape, double scale) {
        double uniformRandom = random.nextDouble();
        return scale * Math.pow(-Math.log(1 - uniformRandom), 1 / shape);
    }

    // Simulate patient arrival
    private void simulatePatientArrival() {
        double shape = 1.5, scale = 1.0 / patientsPerSecond;
        if (random.nextDouble() < generateWeibull(shape, scale)) {
            int triageLevel = random.nextInt(5) + 1;
            int treatmentDuration = (int) Math.ceil(generateWeibull(1.5, 5)); // Treatment time in seconds
            Patient newPatient = new Patient(currentTime, triageLevel, treatmentDuration);
            waitingQueue.add(newPatient);
            totalPatients++; // Increment patient count
            logEvent("New patient added: Triage " + triageLevel + " at time " + currentTime + " days");
        }
    }

    // Assign beds and resources to patients
    private void assignBeds() {
        while (!waitingQueue.isEmpty() && resources.hasAvailableBeds()) {
            Patient patient = waitingQueue.poll();
            resources.assignResources();
            patient.assignBed(currentTime);
            inTreatmentQueue.add(patient);
            logEvent("Patient assigned to bed: Triage " + patient.getTriageCategory() +
                    ", Arrival time " + patient.getArrivalTime());
        }
    }

    // Discharge patients after treatment
    private void dischargePatients() {
        Queue<Patient> treatedPatients = new LinkedList<>();
        for (Patient patient : inTreatmentQueue) {
            if (patient.isTreatmentComplete(currentTime)) {
                resources.releaseResources();
                patient.endTreatment();
                treatedPatients.add(patient);
                totalWaitingTime += (patient.getBedAssignedTime() - patient.getArrivalTime());
                totalTreatmentTime += patient.getTreatmentDuration();
                logEvent("Patient discharged: Triage " + patient.getTriageCategory() +
                        ", Arrival time " + patient.getArrivalTime() +
                        ", Discharge time " + currentTime + " days");
            }
        }
        inTreatmentQueue.removeAll(treatedPatients);
    }

    // Generate summary report
    private void generateSummaryReport() {
        logEvent("----------Summary Report----------");
        logEvent("Total Patients: " + totalPatients);
        logEvent("Average Waiting Time: " + (totalPatients > 0 ? (double) totalWaitingTime / totalPatients : 0) + " days");
        logEvent("Average Treatment Time: " + (totalPatients > 0 ? (double) totalTreatmentTime / totalPatients : 0) + " days");
        logEvent("Doctors to Internee Doctors Ratio: " + resources.getDoctors() + ":" + resources.getInterneeDoctors());
    }

    // Run simulation
    public void runSimulation() {
        logEvent("Simulation started...");
        while (currentTime < simulationTime) {
            simulatePatientArrival();
            dischargePatients();
            assignBeds();
            currentTime++;
        }
        logEvent("Simulation complete. Generating report...");
        generateSummaryReport(); // Add summary report
        generateReport();
    }

    // Generate final report and save it
    public void generateReport() {
    try (FileWriter writer = new FileWriter("simulation_log.txt")) {
        writer.write(logArea.getText());
        logEvent("Report saved to simulation_log.txt.");
    } catch (IOException e) {
        logEvent("Error saving report: " + e.getMessage());
    }
}
}
