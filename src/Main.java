import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    final private static List<Product> products = new ArrayList<>();
    final private static List<Customer> customers = new ArrayList<>();
    final private static List<Driver> drivers = new ArrayList<>();
    final private static List<Order> orders = new ArrayList<>();
    final private static List<Locker> lockers = new ArrayList<>();
    final private static Scanner scanner = new Scanner(System.in);


    public static int checkForNumberInput() {
        int choice = 0;
        while (true) {
            System.out.print("Enter your choice: ");
            //check if given input is number
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number");
                scanner.next();
            }
        }
    }
    public static int checkForNumberInput(String question) {
        int choice = 0;
        while (true) {
            System.out.print(question);
            //check if given input is number
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number");
                scanner.next();
            }
        }
    }
    public static void main(String[] args) {
        //initialize test data
        initProducts();
        initCustomers();
        initDrivers();
        initLockers();
        initOrders();
        boolean running = true;
        while (running) {

            System.out.println("\nDelivery Management System");
            System.out.println("1. Administrator Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Exit");

            int choice = checkForNumberInput();

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
            System.out.println("3. Rate Delivery Service");
            System.out.println("4. Back to Main Menu");

            int choice = checkForNumberInput();
            switch (choice) {
                case 1:
                    registerNewOrderFromUserInputCustomer();
                    break;
                case 2:
                    updateOrderForCustomer();
                    break;
                case 3:
                    rateDeliveryService();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generateReports() {
        boolean running = true;
        while (running) {
            System.out.println("\nGenerate Reports");
            System.out.println("1. Total Orders and Quantities by Barcode or Category");
            System.out.println("2. Total Orders by Driver and Delivery Location");
            System.out.println("3. Average Delivery Service Rating");
            System.out.println("4. Back to Administrator Menu");
            int choice = checkForNumberInput();
            switch (choice) {
                case 1:
                    generateTotalOrdersAndQuantitiesReport();
                    break;
                case 2:
                    generateDriverReport();
                    break;
                case 3:
                    generateRatingReport();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again");
            }
        }

    }

    public static void generateRatingReport() {
        List<Integer> ratings = new ArrayList<>();
        int totalRating = 0;
        //add all ratings and check if final rating is > 0
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getRating() != 0) {
                ratings.add(orders.get(i).getRating());
                totalRating += orders.get(i).getRating();
            }

        }
        if (ratings.isEmpty() || totalRating == 0) {
            System.out.println("No ratings available");
            return;
        }
        double average = 0.0;
        for (int i = 0; i < ratings.size(); i++) {
            average += ratings.get(i);
        }
        //find average rating
        average /= ratings.size();
        int highestRating = ratings.get(0);
        int lowestRating = ratings.get(0);
        for (int i = 1; i < ratings.size(); i++) {
            if (ratings.get(i) > highestRating) {
                highestRating = ratings.get(i);
            }
        }
        for (int i = 1; i < ratings.size(); i++) {
            if (ratings.get(i) < lowestRating) {
                lowestRating = ratings.get(i);
            }
        }
        System.out.println("Lowest rating: " + lowestRating);
        System.out.println("Highest rating: " + highestRating);
        System.out.println("Average rating: " + average);
        List<Customer> customersWithHighestRating = new ArrayList<>();
        List<Customer> customersWithLowestRating = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            //find the customers that gave highest ratings
            if (orders.get(i).getRating() == highestRating) {
                customersWithHighestRating.add(orders.get(i).getCustomer());
            }
            if (orders.get(i).getRating() == lowestRating) {
                //find the customers that gave lowest ratings
                customersWithLowestRating.add(orders.get(i).getCustomer());
            }
        }
        System.out.println("Customers that gave the lowest rating: ");
        //print customers data
        for (int i = 0; i < customersWithLowestRating.size(); i++) {
            System.out.println("customer name: " + customersWithLowestRating.get(i).getName() + " " + customersWithLowestRating.get(i).getSurname());
        }
        System.out.println("Customers that gave the highest rating: ");
        for (int i = 0; i < customersWithHighestRating.size(); i++) {
            System.out.println("customer name: " + customersWithHighestRating.get(i).getName() + " " + customersWithHighestRating.get(i).getSurname());
        }

    }

    public static void generateTotalOrdersAndQuantitiesReport() {
        System.out.println("By barcode:");
        List<String> barcodes = new ArrayList<>();
        List<Integer> quantitiesByBarcodes = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            for (int j = 0; j < order.getProducts().size(); j++) {
                //get products(quantity,barcode) from order
                ProductInformation productInformation = order.getProducts().get(j);
                int quantity = productInformation.getProductQuantity();
                String barcode = productInformation.getProduct().getBarcode();
                int index = barcodes.indexOf(barcode);
                if (index != -1) {
                    //add the quantity to already set barcode
                    quantitiesByBarcodes.set(index, quantitiesByBarcodes.get(index) + quantity);
                } else {
                    //if barcode doesn't exist we add it
                    barcodes.add(barcode);
                    quantitiesByBarcodes.add(quantity);
                }
            }
        }

        for (int i = 0; i < barcodes.size(); i++) {
            System.out.println("Barcode: " + barcodes.get(i) + ", total quantity: " + quantitiesByBarcodes.get(i));
        }
    }

    public static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nDelivery Management System");
            System.out.println("1. Register New Order");
            System.out.println("2. Update Order");
            System.out.println("3. Generate Reports");
            System.out.println("4. Register Product");
            System.out.println("5. Register Driver");
            System.out.println("6. Register Locker");
            System.out.println("7. Search Order");
            System.out.println("8. Exit");

            int choice = checkForNumberInput();


            switch (choice) {
                case 1:
                    registerNewOrderFromUserInput();
                    break;
                case 2:
                    updateOrderForAdmin();
                    break;
                case 3:
                    generateReports();
                    break;
                case 4:
                    registerProduct();
                    break;
                case 5:
                    registerDriver();
                    break;
                case 6:
                    registerLocker();
                    break;
                case 7:
                    searchOrderForAdmin();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void registerLocker() {
        System.out.println("Registering locker. Enter data: ");
        System.out.println("Enter locker address: ");
        String lockerAddress = scanner.nextLine();
        int lockerNumber = checkForNumberInput("Enter locker number: ");
        System.out.println("Is the locker available? (yes/no) ");
        //check if user wrote yes
        boolean isLockerAvailable = scanner.nextLine().equalsIgnoreCase("yes");
        Locker createdLocker = new Locker(isLockerAvailable, lockerNumber, lockerAddress);
        lockers.add(createdLocker);

    }


    private static void searchOrderForAdmin() {
        System.out.println("Please enter Order Id: ");
        String criteria = scanner.nextLine();
        Order found = searchOrder(criteria);
        if (found == null) {
            //show error if not found order
            System.out.println("Order not found with criteria: " + criteria);
            return;
        }
    }

    private static Order searchOrder(String criteria) {

        Order foundOrder = null;
        for (int i = 0; i < orders.size(); i++) {
            //find order with orderId or customer full name
            if (orders.get(i).getOrderID().equals(criteria) || (orders.get(i).getCustomer().getName() + " " + orders.get(i).getCustomer().getSurname()).equalsIgnoreCase(criteria)

            ) {
                foundOrder = orders.get(i);
            }
        }

        if (foundOrder == null) {
            //return null to show error in the calling function
            return null;
        }
        System.out.println("OrderId: " + foundOrder.getOrderID());
        System.out.println("Customer: " + foundOrder.getCustomer().getName() + " " + foundOrder.getCustomer().getSurname());
        System.out.println("Products List");


        for (int i = 0; i < foundOrder.getProducts().size(); i++) {
            ProductInformation productInformation = foundOrder.getProducts().get(i);
            System.out.println("Product name: " + productInformation.getProduct().getName() + ", Quantity: " + productInformation.getProductQuantity());
        }
        if (foundOrder.getAddressOrLockerNum().contains("Locker")) {
            System.out.println("Delivery to Locker");
            System.out.println("Locker Location: " + foundOrder.getAddressOrLockerNum());
        } else {
            System.out.println("Delivery to Customer Address");
            System.out.println("Customer Address: " + foundOrder.getAddressOrLockerNum());
        }
        System.out.println("Order Status: " + foundOrder.getStatus());
        return foundOrder;
    }


    private static Order selectOrderForAdmin() {
        System.out.println("Available orders");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(i + ". " + orders.get(i).getOrderID());
        }
        System.out.println("Enter order id to update: ");
        String orderID = scanner.nextLine();
        Order toUpdate = null;
        for (int i = 0; i < orders.size(); i++) {
            //find order by order ID
            if (orders.get(i).getOrderID().equals(orderID)) {
                toUpdate = orders.get(i);
            }
        }
        if (toUpdate == null) {
            System.out.println("Order not found with: " + orderID);
            return null;
        }
        return toUpdate;
    }

    public static void updateOrderForAdmin() {
        Order foundOrder = selectOrderForAdmin();
        if (foundOrder == null) {
            //show error msg if order not found
            System.out.println("Order not found");
            return;
        }
        Driver newDriver = selectDriver();
        foundOrder.setDriver(newDriver);
        System.out.println("Updated order successfully, new driver is " + newDriver.getName() + " " + newDriver.getSurname());
        System.out.println(foundOrder);

    }

    private static void updateOrderForCustomer() {
        System.out.println("Enter your Order ID or your full name  to find your order: ");
        String criteria = scanner.nextLine();
        Order foundOrderToUpdate = searchOrder(criteria);
        if (foundOrderToUpdate == null) {
            //show error msg if order not found
            System.out.println("Order not found with your search query: " + criteria);
            return;
        }
        System.out.println(foundOrderToUpdate);
        System.out.println("Would you like to\n 1.update the delivery address \n 2.set the order as completed?");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                updateDeliveryAddress(foundOrderToUpdate);
                break;
            case 2:
                setOrderAsCompleted(foundOrderToUpdate);
                break;

        }
    }

    private static void updateDeliveryAddress(Order toUpdate) {
        if (toUpdate.getAddressOrLockerNum().startsWith("Locker")) {
            System.out.println("You have chosen delivery to locker method, you cannot change delivery address");
            return;
        }
        System.out.println("Enter new delivery address: ");
        String newAddress = scanner.nextLine();
        toUpdate.setAddressOrLockerNum(newAddress);
        System.out.println("Delivery address changed successfully");
        System.out.println(toUpdate);
    }

    private static void setOrderAsCompleted(Order toUpdate) {
        if (toUpdate.getStatus().equalsIgnoreCase("completed")) {
            System.out.println("Your order is already completed");
            return;
        }

        if (toUpdate.getAddressOrLockerNum().startsWith("Locker")) {
            String lockerAddress = toUpdate.getAddressOrLockerNum().split(" at ")[1].split(" #")[0];
            int lockerNum = Integer.parseInt(toUpdate.getAddressOrLockerNum().split("#")[1]);
            Locker foundLocker = null;
            for (int i = 0; i < lockers.size(); i++) {
                //find locker with locker address and locker num
                if (lockers.get(i).getAddress().equals(lockerAddress) && lockers.get(i).getNumber() == lockerNum) {
                    foundLocker = lockers.get(i);
                }
            }
            if (foundLocker == null) {
                System.out.println("Something went wrong with the locker. Please contact administrator");
                return;
            }
            //make it available if order is complete
            foundLocker.setAvailable(true);
        }
        toUpdate.setStatus("completed");
        System.out.println("Order code: " + toUpdate.getOrderID());
        System.out.println("Driver's full name: " + toUpdate.getDriver().getName() + " " + toUpdate.getDriver().getSurname());
        System.out.println("Delivery address: " + toUpdate.getAddressOrLockerNum());
        System.out.println("Order status: " + toUpdate.getStatus());
        System.out.println("Your order was completed successfully");
    }


    private static Driver registerDriver() {
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

        Driver newDriver = new Driver(driverName, driverDeliversToCustomer, driverDeliversToLockers, driverVRN, driverAFM, driverEmail, driverAddress, driverSurname);


        drivers.add(newDriver);

        System.out.println("Driver registered successfully");
        return newDriver;

    }

    private static void registerProduct() {
        System.out.println("Registering new product");

        System.out.println("Enter barcode: ");

        String barcode = scanner.nextLine();

        System.out.println("Enter product name: ");

        String productName = scanner.nextLine();

        System.out.println("enter product brand: ");

        String productBrand = scanner.nextLine();

        System.out.println("enter product category (e.g., food, detergent, hygiene, beverage): ");
        String categoryStr = scanner.nextLine();
        Product newProduct = new Product(barcode, productName, productBrand, categoryStr);
        products.add(newProduct);
        System.out.println("Product registered successfully");
    }

    public static void registerNewOrderFromUserInputCustomer() {
        System.out.println("Register New Order");
        System.out.println("Customer Full Name: ");
        String customerFullName = scanner.nextLine();
        Customer foundCustomer = null;
        for (int i = 0; i < customers.size(); i++) {
            //check if customer has an account already
            if ((customers.get(i).getName() + " " + customers.get(i).getSurname()).equalsIgnoreCase(customerFullName)) {
                foundCustomer = customers.get(i);
            }
        }
        //if customer doesn't have account , create it
        if (foundCustomer == null) {
            System.out.println("Customer with full name: " + customerFullName + " not found. Enter your data to register");
            System.out.println("Enter your first name: ");
            String customerFirstName = scanner.nextLine();
            System.out.println("Enter your surname: ");
            String customerSurname = scanner.nextLine();
            System.out.println("Enter your address: ");
            String customerAddress = scanner.nextLine();
            System.out.println("Enter your email: ");
            String customerEmail = scanner.nextLine();
            foundCustomer = new Customer(customerEmail, customerAddress, customerSurname, customerFirstName);
            System.out.println("New user registered successfully");
        }
        customers.add(foundCustomer);
        Driver driver = selectDriverForCustomer();
        List<ProductInformation> products = selectProducts();

        System.out.print("Deliver to locker? (yes/no): ");
        scanner.nextLine();

        boolean deliverToLocker = scanner.nextLine().equalsIgnoreCase("yes");

        registerNewOrder(foundCustomer, driver, products, deliverToLocker);


    }

    public static void registerNewOrderFromUserInput() {
        System.out.println("Register New Order");
        Customer customer = selectCustomer();
        Driver driver = selectDriver();
        List<ProductInformation> products = selectProducts();
        System.out.print("Deliver to locker? (yes/no): ");
        scanner.nextLine();
        boolean deliverToLocker = scanner.nextLine().equalsIgnoreCase("yes");
        registerNewOrder(customer, driver, products, deliverToLocker);
    }

    public static void registerNewOrder(Customer customer, Driver driver, List<ProductInformation> products, boolean deliversToLocker) {
        //generate random number
        String orderId = UUID.randomUUID().toString();
        String deliveryAddress;
        if (deliversToLocker) {
            Locker available = null;
            for (int i = 0; i < lockers.size(); i++) {
                if (lockers.get(i).isAvailable()) {
                    available = lockers.get(i);
                }
            }
            if (available == null) {
                System.out.println("None of our lockers are available, please try again later");
                return;
            }
            deliveryAddress = "Locker at " + available.getAddress() + " #" + available.getNumber();
            //make locker not available
            available.setAvailable(false);
        } else {
            deliveryAddress = customer.getAddress();
        }
        Order newOrder = new Order(orderId, products, deliveryAddress, "pending", driver, customer);

        System.out.println("Order id: " + newOrder.getOrderID());
        System.out.println("Customer full name: " + newOrder.getCustomer().getName() + " " + newOrder.getCustomer().getSurname());
        System.out.println("Driver full name: " + newOrder.getDriver().getName() + " " + newOrder.getDriver().getSurname());
        if (newOrder.getAddressOrLockerNum().contains("Locker")) {
            System.out.println("Locker address: " + newOrder.getAddressOrLockerNum());
        }
        orders.add(newOrder);
        System.out.println("Order registered successfully");
    }

    private static Customer createCustomer() {
        System.out.println("Register New Customer");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String address = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        Customer newCustomer = new Customer(email, address, surname, name);
        customers.add(newCustomer);
        System.out.println("Customer registered successfully: " + newCustomer);
        return newCustomer;
    }


    public static Customer selectCustomer() {
        while (true) {
            System.out.println("Select Customer:");
            for (int i = 0; i < customers.size(); i++) {
                //select with name and surname
                System.out.println((i + 1) + ". " + customers.get(i).getName() + " " + customers.get(i).getSurname());
            }

            int number = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Enter customer number (or 0 to create new customer): ");
                try {
                    number = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number");
                    scanner.next();
                }
            }
            int customerIndex = number - 1;
            scanner.nextLine();
            if (customerIndex == -1) {
                return createCustomer();
            } else if (customerIndex >= 0 && customerIndex < customers.size()) {
                return customers.get(customerIndex);
            } else {
                System.out.println("Invalid customer , try again ");
            }
        }
    }

    private static Driver selectDriverForCustomer() {
        while (true) {
            System.out.println("Select Driver:");
            for (int i = 0; i < drivers.size(); i++) {
                //select with name and surname
                System.out.println((i + 1) + ". " + drivers.get(i).getName() + " " + drivers.get(i).getSurname());
            }
            int number = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Enter driver number: ");

                try {
                    number = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number");
                    scanner.next();
                }
            }

            int driverIndex = number - 1;
            scanner.nextLine();
            if (driverIndex >= 0 && driverIndex < drivers.size()) {
                return drivers.get(driverIndex);
            } else {
                System.out.println("Invalid driver number. Please try again.");
            }
        }
    }

    private static Driver selectDriver() {
        while (true) {
            System.out.println("Select Driver:");
            for (int i = 0; i < drivers.size(); i++) {
                //select with name and surname
                System.out.println((i + 1) + ". " + drivers.get(i).getName() + " " + drivers.get(i).getSurname());
            }
            int number = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Enter driver number (or 0 to create new driver): ");

                try {
                    number = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number");
                    scanner.next();
                }
            }
            int driverIndex = number - 1;
            scanner.nextLine();
            if (driverIndex == -1) {
                //create driver choice
                return registerDriver();
            } else if (driverIndex >= 0 && driverIndex < drivers.size()) {
                return drivers.get(driverIndex);
            } else {
                System.out.println("Invalid driver number. Please try again.");
            }
        }
    }

    private static List<ProductInformation> selectProducts() {
        List<ProductInformation> productsSelected = new ArrayList<>();
        System.out.println("Select Products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". Barcode: " + products.get(i).getBarcode() + ", Name: " + products.get(i).getName() + ", category: (" + products.get(i).getCategory() + ")");
        }
        while (true) {
            int productIndex = 0;
            while (true) {
                System.out.print("Enter product number (or 0 to finish): ");
                try {
                    productIndex = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number");
                    scanner.next();
                }
            }
            //check if user typed 0
            if (productIndex - 1 == -1) {
                System.out.println("finished adding products");
                break;
            }
            if (productIndex >= 0 && productIndex < products.size()) {
                boolean validInput = false;
                while (!validInput) {
                    System.out.print("Enter right quantity: ");
                    try {
                        //check for valid user input
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        productsSelected.add(new ProductInformation(products.get(productIndex), quantity));
                        validInput = quantity >= 1;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("Invalid product number. Please try again.");
            }
        }
        return productsSelected;
    }


    private static void rateDeliveryService() {
        System.out.println("Enter order id to rate:");
        String OrderId = scanner.nextLine();
        Order foundOrder = null;
        for (int i = 0; i < orders.size(); i++) {
            //find order with order id
            if (orders.get(i).getOrderID().equals(OrderId)) {
                foundOrder = orders.get(i);
            }
        }
        if (foundOrder == null) {
            System.out.println("Order with id: " + OrderId + " not found");
            return;
        }
        if (!foundOrder.getStatus().equalsIgnoreCase("completed")) {
            System.out.println("Order with id: " + OrderId + " hasn't been completed yet");
            return;
        }
        System.out.println("Enter rating (1-10)");
        int rating = scanner.nextInt();
        scanner.nextLine();
        if (rating < 1 || rating > 10) {
            System.out.println("Invalid rating. Please select a number between 1 and 10");
            return;
        }
        foundOrder.setRating(rating);
        System.out.println("Rating submitted successfully");
    }

    public static void generateDriverReport() {
        for (int i = 0; i < drivers.size(); i++) {
            Driver driver = drivers.get(i);
            System.out.println("--------------------------------------------------");
            System.out.println("Driver: " + driver.getName() + " " + driver.getSurname());
            int totalOrders = 0;
            int lockerDeliveries = 0;
            int customerLocationDeliveries = 0;
            int pendingOrders = 0;
            int completedOrders = 0;
            //show data for each driver
            for (int j = 0; j < orders.size(); j++) {
                Order order = orders.get(j);
                if (order.getDriver().equals(driver)) {
                    totalOrders++;
                    if (order.getAddressOrLockerNum().contains("Locker")) {
                        lockerDeliveries++;
                    } else {
                        customerLocationDeliveries++;
                    }
                    if (order.getStatus().equalsIgnoreCase("pending")) {
                        pendingOrders++;
                    } else if (order.getStatus().equalsIgnoreCase("completed")) {
                        completedOrders++;
                    }
                }
            }
            System.out.println("Total orders: " + totalOrders);
            System.out.println("Locker deliveries: " + lockerDeliveries);
            System.out.println("Customer address deliveries: " + customerLocationDeliveries);
            System.out.println("Pending orders: " + pendingOrders);
            System.out.println("Completed orders: " + completedOrders);
        }
    }

    //TEST DATA INITIALIZATION
    private static void initProducts() {
        products.add(new Product("1234567890", "Milk", "Delta", "food"));
        products.add(new Product("0987654321", "Soap", "example_brand", "hygiene"));
        products.add(new Product("0933654161", "computer", "HP", "Technology"));
        products.add(new Product("0945658961", "chair", "furniture", "IKEA"));
        products.add(new Product("0943254321", "table", "furniture", "IKEA"));
    }

    private static void initCustomers() {
        customers.add(new Customer("john@example.com", "123 Elm St", "Doe", "John"));
        customers.add(new Customer("jane@example.com", "426 Maple Ave", "Smith", "Jane"));
        customers.add(new Customer("george@example.com", "156 Apple Ave", "Carter", "George"));
        customers.add(new Customer("daniel@example.com", "87 Orange Ave", "Snow", "Daniel"));
    }

    private static void initDrivers() {
        drivers.add(new Driver("Alice", false, true, "XYZ123", "123456789", "alie@gmail.com", "789 Oak St", "Johnson"));
        drivers.add(new Driver("Bob", false, true, "XYZ357", "123674767", "bob@gmail.com", "789 Fake St", "Journal"));
        drivers.add(new Driver("George", true, true, "XBZ567", "123123767", "george@gmail.com", "789 Unreal St", "Carter"));
    }

    private static void initLockers() {
        lockers.add(new Locker(true, 100, "Main street"));
        lockers.add(new Locker(false, 120, "Sunday Boulevard"));
    }

    private static void initOrders() {
        List<ProductInformation> productsSelected = new ArrayList<>();
        productsSelected.add(new ProductInformation(new Product("egf4355466", "milk", "test brand", "food"), 10));
        productsSelected.add(new ProductInformation(new Product("egf4355488", "cereal", "test brand", "food"), 5));
        Order newOrder1 = new Order(UUID.randomUUID().toString(), productsSelected, "test address 1", "pending", drivers.get(0), customers.get(0));
        Order newOrder2 = new Order(UUID.randomUUID().toString(), productsSelected, "Locker at Main street #100", "pending", drivers.get(1), customers.get(1));
        Order newOrder3 = new Order(UUID.randomUUID().toString(), productsSelected, "Locker at Sunday Boulevard #120", "pending", drivers.get(1), customers.get(0));
        orders.add(newOrder1);
        orders.add(newOrder2);
        orders.add(newOrder3);
    }
}