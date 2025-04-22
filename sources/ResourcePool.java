package App;
public class ResourcePool {
    // Attributes
    private int availableBeds;
    private final int doctors;
    private final int interneeDoctors;

    // Constructor
    public ResourcePool(int availableBeds, int doctors, int interneeDoctors) {
        this.availableBeds = availableBeds;
        this.doctors = doctors;
        this.interneeDoctors = interneeDoctors;
    }

    // Assign resources
    public void assignResources() {
        if (availableBeds > 0) availableBeds--;
    }

    // Release resources
    public void releaseResources() {
        availableBeds++;
    }

    // Check if beds are available
    public boolean hasAvailableBeds() {
        return availableBeds > 0;
    }

    // Get the number of doctors
    public int getDoctors() {
        return doctors;
    }

    // Get the number of internee doctors
    public int getInterneeDoctors() {
        return interneeDoctors;
    }
}
