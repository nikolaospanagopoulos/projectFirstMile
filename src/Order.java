import java.util.List;
import java.util.Map;

public class Order {
    private String orderID;
    private Customer customer;
    private Driver driver;
    private String status;
    private String addressOrLockerNum;
    private List<ProductInformation> products;
    private String fullAddress;
    private int rating;


    public Order(String orderID, List<ProductInformation> products, String addressOrLockerNum, String status, Driver driver, Customer customer,String fullAddress) {
        this.orderID = orderID;
        this.products = products;
        this.addressOrLockerNum = addressOrLockerNum;
        this.status = status;
        this.driver = driver;
        this.customer = customer;
        this.fullAddress = fullAddress;
        this.rating = 0;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getAddressOrLockerNum() {
        return addressOrLockerNum;
    }

    public void setAddressOrLockerNum(String addressOrLockerNum) {
        this.addressOrLockerNum = addressOrLockerNum;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductInformation> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInformation> products) {
        this.products = products;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", customer=" + customer +
                ", driver=" + driver +
                ", status='" + status + '\'' +
                ", addressOrLockerNum='" + addressOrLockerNum + '\'' +
                ", products=" + products +
                ", fullAddress='" + fullAddress + '\'' +
                ", rating=" + rating +
                '}';
    }
}
