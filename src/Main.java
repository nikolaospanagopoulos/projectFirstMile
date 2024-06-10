import java.sql.Array;
import java.sql.SQLOutput;
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
                    registerNewOrderFromUserInput();
                    break;
                case 2:
                    updateOrderForCustomer();
                    break;
                case 3:
                    // searchOrderFromInput();
                    break;
                case 4:
                    rateDeliveryService();
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
            System.out.println("3. Generate Driver Report");
            System.out.println("4. Register Product");
            System.out.println("5. Register Driver");
            System.out.println("6. Register Locker");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerNewOrderFromUserInput();
                    break;
                case 2:
                    updateOrderForAdmin();
                    break;
                case 3:
                    generateDriverReport();
                    break;
                case 5:
                    registerDriver();
                    break;
                case 4:
                    registerProduct();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static Order selectOrderForAdmin(){
        System.out.println("Available orders");
        for(int i=0;i<orders.size();i++){
            System.out.println(i+". "+orders.get(i).getOrderID());
        }
        System.out.println("Enter order id to update: ");
        String orderID = scanner.nextLine();
        Order toUpdate = null;

        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getOrderID().equals(orderID)){
                toUpdate = orders.get(i);
            }
        }

        if(toUpdate==null){
            System.out.println("Order not found with: "+orderID);
            return null;
        }
        return toUpdate;
    }

    public static void updateOrderForAdmin(){
         Order foundOrder = selectOrderForAdmin();
         if(foundOrder==null){
             return;
         }
         Driver newDriver = selectDriver();
         foundOrder.setDriver(newDriver);
        System.out.println("Updated order successfully, new driver is "+newDriver.getName()+" "+newDriver.getSurname());
        System.out.println(foundOrder);

    }

    private static void updateOrderForCustomer(){
        System.out.println("Enter your Order ID or your full name  to find your order: ");
        String criteria  = scanner.nextLine();
        Order foundOrderToUpdate = searchOrder(criteria);
        if(foundOrderToUpdate == null){
            System.out.println("Order not found with your search query: "+criteria);
            return;
        }
        System.out.println(foundOrderToUpdate);
        System.out.println("Would you like to\n 1.update the delivery address \n 2.set the order as completed?");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                updateDeliveryAddress(foundOrderToUpdate);
                break;
            case 2:
                setOrderAsCompleted(foundOrderToUpdate);
                break;

        }
    }
    private static void updateDeliveryAddress(Order toUpdate){
        if(toUpdate.getAddressOrLockerNum().startsWith("Locker")){
            System.out.println("You have chosen delivery to locker method, you cannot change delivery address");
            return;
        }
        System.out.println("Enter new delivery address: ");
        String newAddress = scanner.nextLine();
        toUpdate.setAddressOrLockerNum(newAddress);
        System.out.println("Delivery address changed successfully");
        System.out.println(toUpdate);
    }

    private static void setOrderAsCompleted(Order toUpdate){
        if(toUpdate.getStatus().equalsIgnoreCase("completed")){
            System.out.println("Your order is already completed");
            return;
        }

        if(toUpdate.getAddressOrLockerNum().startsWith("Locker")){
            String lockerAddress=toUpdate.getAddressOrLockerNum().split(" at ")[1].split(" #")[0];
            int lockerNum = Integer.parseInt(toUpdate.getAddressOrLockerNum().split("#")[1]);
            Locker foundLocker = null;
            for(int i = 0;i<lockers.size();i++){
                if(lockers.get(i).getAddress().equals(lockerAddress)&&lockers.get(i).getNumber()==lockerNum){
                    foundLocker = lockers.get(i);
                }
            }
            if(foundLocker==null){
                System.out.println("Something went wrong with the locker. Please contact administrator");
                return;
            }
            foundLocker.setAvailable(true);

        }


        toUpdate.setStatus("completed");
        System.out.println("Order code: "+toUpdate.getOrderID());
        System.out.println("Driver's full name: "+toUpdate.getDriver().getName()+" "+toUpdate.getDriver().getSurname());
        System.out.println("Delivery address: "+toUpdate.getFullAddress());
        System.out.println("Order status: "+toUpdate.getStatus());
        System.out.println("Your order was completed successfully");
    }

    private static Order searchOrder(String criteria){

        Order foundOrder = null;
        for(int i = 0;i<orders.size();i++){
            if(orders.get(i).getOrderID().equals(criteria)|| (orders.get(i).getCustomer().getName()+" "+orders.get(i).getCustomer().getSurname()).equalsIgnoreCase(criteria)

            ){
                foundOrder = orders.get(i);
            }
        }

        if(foundOrder == null){
            return null;
        }

        System.out.println("OrderId: "+foundOrder.getOrderID());
            System.out.println("Customer: "+foundOrder.getCustomer().getName());
            System.out.println("   Products List   ");

            //fix this
            for(Map.Entry<Product,Integer>entry:foundOrder.getProducts().entrySet()){
                System.out.println("Product: "+entry.getKey()+" quantity "+entry.getValue());
            }
            if(foundOrder.getAddressOrLockerNum().contains("Locker")){
                System.out.println("Locker Location: "+foundOrder.getAddressOrLockerNum());
            }else{
                System.out.println("Customer Address: "+foundOrder.getAddressOrLockerNum());
            }
            System.out.println("Order Status: "+foundOrder.getStatus());
            return foundOrder;
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
        scanner.nextLine();
        boolean deliverToLocker = scanner.nextLine().equalsIgnoreCase("yes");
        System.out.println("DELIVERS TO LOCKER"+deliverToLocker);
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
        Order newOrder = new Order(orderId,products,deliveryAddress,"pending",driver,customer,deliveryAddress);

        System.out.println(newOrder);
        orders.add(newOrder);
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



private static void rateDeliveryService(){
    System.out.println("Enter order id to rate:");
    String OrderId = scanner.nextLine();
    Order foundOrder = null;
    for (int i = 0;i<orders.size();i++){
        if(orders.get(i).getOrderID().equals(OrderId)){
            foundOrder = orders.get(i);
        }
    }
    if(foundOrder==null){
        System.out.println("Order with id: "+OrderId+" not found");
        return;
    }
    if(!foundOrder.getStatus().equalsIgnoreCase("completed")){
        System.out.println("Order with id: "+OrderId+" hasn't been completed yet");
        return;
    }
    System.out.println("Enter rating (1-10)");
    int rating = scanner.nextInt();
    scanner.nextLine();
    if(rating<1||rating>10){
        System.out.println("Invalid rating. Please select a number between 1 and 10");
        return;
    }
    foundOrder.setRating(rating);
    System.out.println("Rating submitted successfully");
}

public static void generateDriverReport(){
    List<Driver>driversFound = new ArrayList<>();
    for(int i = 0;i<orders.size();i++){
        if(!driversFound.contains(orders.get(i).getDriver())){
            driversFound.add(orders.get(i).getDriver());
        }
    }


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