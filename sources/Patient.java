package App;
public class Patient {
    // Attributes
    private final int arrivalTime;
    private final int triageCategory;
    private int bedAssignedTime;
    private final int treatmentDuration;
    private boolean treatmentComplete;

    // Constructor
    public Patient(int arrivalTime, int triageCategory, int treatmentDuration) {
        this.arrivalTime = arrivalTime;
        this.triageCategory = triageCategory;
        this.treatmentDuration = treatmentDuration;
        this.bedAssignedTime = -1; // -1 indicates bed not assigned
        this.treatmentComplete = false;
    }

    // Getters
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getTriageCategory() {
        return triageCategory;
    }

    public int getBedAssignedTime() {
        return bedAssignedTime;
    }

    public int getTreatmentDuration() {
        return treatmentDuration;
    }

    // Assign bed and start treatment
    public void assignBed(int currentTime) {
        this.bedAssignedTime = currentTime;
    }

    // Check if treatment is complete
    public boolean isTreatmentComplete(int currentTime) {
        return currentTime - bedAssignedTime >= treatmentDuration;
    }

    // Mark treatment as complete
    public void endTreatment() {
        this.treatmentComplete = true;
    }
}
