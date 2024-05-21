import java.util.*;

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
        initProducts();
        initCustomers();
        initDrivers();
        initLockers();
        boolean running = true;
        while(running){

            System.out.println("\nDelivery Management System");
            System.out.println("1. Administrator Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }

    private static void customerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nCustomer Menu");
            System.out.println("1. Register New Order");
            System.out.println("2. Update Order");
            System.out.println("3. Search Order");
            System.out.println("4. Rate Delivery Service");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // registerNewOrderFromInput();
                    break;
                case 2:
                    //  updateOrderFromInput();
                    break;
                case 3:
                    // searchOrderFromInput();
                    break;
                case 4:
                    //    rateDeliveryService();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



    public static void adminMenu(){
        boolean running = true;
        while (running) {
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
            switch (choice) {
                case 1:
                    registerNewOrderFromUserInput();
                    break;
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
    private static Driver registerDriver(){
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
        return newDriver;

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

    public static void registerNewOrderFromUserInput(){
        System.out.println("Register New Order");
        Customer customer = selectCustomer();
        Driver driver = selectDriver();
        HashMap<Product, Integer> products = selectProducts();
        System.out.print("Deliver to locker? (yes/no): ");
        boolean deliverToLocker = scanner.nextLine().equalsIgnoreCase("yes");
        scanner.nextLine();
        registerNewOrder(customer,driver,products,deliverToLocker);
    }

    public static void registerNewOrder(Customer customer,Driver driver, HashMap<Product, Integer> products, boolean deliversToLocker){
        String orderId = UUID.randomUUID().toString();
        String deliveryAddress;
        if(deliversToLocker){
            Locker available = null;
            for(int i = 0;i<lockers.size();i++){
                if(lockers.get(i).isAvailable()){
                    available = lockers.get(i);
                }
            }
            if(available == null){
                System.out.println("no available locker, please try again later");
                return;
            }
            deliveryAddress = "Locker at "+available.getAddress() + " #"+available.getNumber();
            available.setAvailable(false);
        }else{
            deliveryAddress = customer.getAddress();
        }
        Order newOrder = new Order(orderId,products,deliveryAddress,"pending",driver,customer);
        System.out.println(newOrder);
    }














    private static Customer createCustomer(){
        System.out.println("Register New Customer");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String address = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        Customer newCustomer = new Customer(email,address,surname,name);
        customers.add(newCustomer);
        System.out.println("Customer registered successfully: " + newCustomer);
        return newCustomer;
    }





    public static Customer selectCustomer(){
        while(true){
            System.out.println("Select Customer:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i + 1) + ". " + customers.get(i).getName() + " " + customers.get(i).getSurname());
            }
            System.out.print("Enter customer number (or 0 to create new customer): ");
            int customerIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            if(customerIndex == -1){
                return createCustomer();
            }else if(customerIndex >= 0 && customerIndex<customers.size()){
                return customers.get(customerIndex);
            }else{
                System.out.println("Invalid customer , try again ");
            }

        }
    }

    private static Driver selectDriver() {

        while(true){
            System.out.println("Select Driver:");
            for (int i = 0; i < drivers.size(); i++) {
                System.out.println((i + 1) + ". " + drivers.get(i).getName() + " " + drivers.get(i).getSurname());
            }
            System.out.print("Enter driver number (or 0 to create new driver): ");
            int driverIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            if(driverIndex == -1){
                return   registerDriver();
            }else if(driverIndex >= 0 && driverIndex<drivers.size()){
                return drivers.get(driverIndex);
            }else{
                System.out.println("Invalid driver number. Please try again.");
            }

        }








    }

    private static HashMap<Product,Integer>selectProducts(){
        HashMap<Product, Integer> productsSelected = new HashMap<>();
        System.out.println("Select Products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getName() + " (" + products.get(i).getCategory() + ")");
        }

        while(true){
            System.out.print("Enter product number (or 0 to finish): ");
            int productIndex = scanner.nextInt() - 1;
            if (productIndex == -1) {
                System.out.println("finised adding products");
                break;
            }

            if(productIndex >= 0 && productIndex<products.size()){
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                productsSelected.put(products.get(productIndex), quantity);
            }else{
                System.out.println("Invalid product number. Please try again.");
            }




        }
        return productsSelected;
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