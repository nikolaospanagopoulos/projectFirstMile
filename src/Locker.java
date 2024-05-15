public class Locker {
    private String address;
    private int number;
    private boolean isAvailable;

    public Locker(boolean isAvailable, int number, String address) {
        this.isAvailable = isAvailable;
        this.number = number;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Locker{" +
                "address='" + address + '\'' +
                ", number=" + number +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
