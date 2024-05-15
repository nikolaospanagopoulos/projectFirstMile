import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Driver> drivers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<Locker> lockers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean running = true;
        while(running){
            System.out.println("\nDelivery Management System");
            System.out.println("1. Register New Order");
            System.out.println("2. Update Order");
            System.out.println("3. Search Order");
            System.out.println("4. Generate Driver Report");
            System.out.println("5. Register Product");
            System.out.println("6. Register Driver");
            System.out.println("7. Register Locker");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 6:
                    registerDriver();
                    break;
                case 5:
                    registerProduct();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void registerDriver(){
        System.out.println("Registering new driver");
        System.out.println("Enter driver name: ");
        String driverName = scanner.nextLine();
        System.out.println("Enter driver surname: ");
        String driverSurname = scanner.nextLine();

        System.out.println("Enter driver address: ");
        String driverAddress = scanner.nextLine();

        System.out.println("Enter driver email: ");
        String driverEmail = scanner.nextLine();

        System.out.println("Enter driver AFM: ");
        String driverAFM = scanner.nextLine();

        System.out.println("Enter driver vehicle registration number: ");
        String driverVRN = scanner.nextLine();
        System.out.println("Delivers to lockers?(yes/no): ");
        boolean driverDeliversToLockers = scanner.nextLine().equalsIgnoreCase("yes");
        System.out.println("Delivers to customer?(yes/no): ");
        boolean driverDeliversToCustomer = scanner.nextLine().equalsIgnoreCase("yes");

        Driver newDriver = new Driver(driverName,driverDeliversToCustomer,driverDeliversToLockers,driverVRN,driverAFM,driverEmail,driverAddress,driverSurname);

        drivers.add(newDriver);

        System.out.println(newDriver);

    }
    private static void registerProduct(){
        System.out.println("Registering new product");

        System.out.println("Enter barcode: ");

        String barcode = scanner.nextLine();

        System.out.println("Enter product name: ");

        String productName = scanner.nextLine();

        System.out.println("enter peoduct brand: ");

        String productBrand = scanner.nextLine();

        System.out.println("enter product category (e.g., food, detergent, hygiene, beverage): ");

        String categoryStr =  scanner.nextLine();

        Product newProduct = new Product(barcode,productName,productBrand,categoryStr);

        products.add(newProduct);


    }

    private static void initProducts(){
        products.add(new Product("1234567890", "Milk", "food", "BrandA"));
        products.add(new Product("0987654321", "Soap", "hygiene", "BrandB"));
    }
    private static void initCustomers(){
        customers.add(new Customer("john@example.com", "123 Elm St", "Doe", "john"));
        customers.add(new Customer("jane@example.com", "456 Maple Ave", "Smith", "jane@example.com"));
    }
    private static void initDrivers(){
        drivers.add(new Driver("Alice", false, true, "XYZ123", "123456789", "alie@gmail.com", "789 Oak St", "Johnson"));
        drivers.add(new Driver("Bob", false, true, "XYZ357", "123674767", "bob@gmail.com", "789 Fake St", "Jurnal"));
    }

    private static void  initLockers(){
        lockers.add(new Locker(true, 100, "oti na nai street"));
        lockers.add(new Locker(false, 120, "oti ki oti street"));

    }
}