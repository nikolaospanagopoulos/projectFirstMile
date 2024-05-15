public class Driver {
    private String name;
    private String surname;
    private String address;
    private String email;
    private String AFM;
    private String vehicleRegistrationNumber;
    private boolean deliversToLockers;
    private boolean deliversToCustomer;

    public Driver(String name, boolean deliversToCustomer, boolean deliversToLockers, String vehicleRegistrationNumber, String AFM, String email, String address, String surname) {
        this.name = name;
        this.deliversToCustomer = deliversToCustomer;
        this.deliversToLockers = deliversToLockers;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.AFM = AFM;
        this.email = email;
        this.address = address;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
