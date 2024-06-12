public class Driver extends Customer{
    private String AFM;
    private String vehicleRegistrationNumber;
    private boolean deliversToLockers;
    private boolean deliversToCustomer;

    public Driver(String name, boolean deliversToCustomer, boolean deliversToLockers, String vehicleRegistrationNumber, String AFM, String email, String address, String surname) {
        super(email,address,surname,name);
        this.deliversToCustomer = deliversToCustomer;
        this.deliversToLockers = deliversToLockers;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.AFM = AFM;
    }

    public String getAFM() {
        return AFM;
    }

    public void setAFM(String AFM) {
        this.AFM = AFM;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public boolean isDeliversToLockers() {
        return deliversToLockers;
    }

    public void setDeliversToLockers(boolean deliversToLockers) {
        this.deliversToLockers = deliversToLockers;
    }

    public boolean isDeliversToCustomer() {
        return deliversToCustomer;
    }

    public void setDeliversToCustomer(boolean deliversToCustomer) {
        this.deliversToCustomer = deliversToCustomer;
    }
    public boolean deliversToLockers(){
        return deliversToLockers;
    }
    public boolean deliversToCustomers(){
        return deliversToCustomer;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", AFM='" + AFM + '\'' +
                ", vehicleRegistrationNumber='" + vehicleRegistrationNumber + '\'' +
                ", deliversToLockers=" + deliversToLockers +
                ", deliversToCustomer=" + deliversToCustomer +
                '}';
    }
}
