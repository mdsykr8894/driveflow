import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class CarRentalSystem {
    private ArrayList<Car> carList;
    private ArrayList<Customer> customerList;
    private ArrayList<Rental> rentalList;
    private ArrayList<User> userList;

    private final String DATA_FOLDER = "data";
    private final String REPORT_FOLDER = "reports";

    public CarRentalSystem() {
        carList = new ArrayList<>();
        customerList = new ArrayList<>();
        rentalList = new ArrayList<>();
        userList = new ArrayList<>();

        createDataFilesIfMissing();
        loadData();
        createDefaultAdminIfNeeded();
    }

    public User login(String username, String password) {
        for (User user : userList) {
            if (user.validateLogin(username, password)) {
                if ("DEACTIVATED".equalsIgnoreCase(user.getStatus())) {
                    return null;
                }
                return user;
            }
        }
        return null;
    }

    public boolean isUserDeactivated(String username, String password) {
        for (User user : userList) {
            if (user.validateLogin(username, password)) {
                return "DEACTIVATED".equalsIgnoreCase(user.getStatus());
            }
        }
        return false;
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(userList);
    }

    public ArrayList<User> getStaffUsers() {
        ArrayList<User> staffUsers = new ArrayList<>();

        for (User user : userList) {
            if ("Staff".equalsIgnoreCase(user.getRole())) {
                staffUsers.add(user);
            }
        }

        return staffUsers;
    }

    public boolean usernameExists(String username) {
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean addStaff(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        if (usernameExists(username)) {
            return false;
        }

        userList.add(new Staff(username.trim(), password.trim()));
        saveUsers();
        return true;
    }

    public boolean toggleUserStatus(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if ("admin".equalsIgnoreCase(username.trim())) {
            return false;
        }

        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username.trim())) {
                if ("ACTIVE".equalsIgnoreCase(user.getStatus())) {
                    user.setStatus("DEACTIVATED");
                } else {
                    user.setStatus("ACTIVE");
                }
                saveUsers();
                return true;
            }
        }
        return false;
    }

    private void createDefaultAdminIfNeeded() {
        boolean hasAdmin = false;

        for (User user : userList) {
            if ("Admin".equalsIgnoreCase(user.getRole())) {
                hasAdmin = true;
                break;
            }
        }

        if (!hasAdmin) {
            userList.add(new Admin("admin", "admin123"));
            saveUsers();
        }
    }

    public ArrayList<Car> getAllCars() {
        return new ArrayList<>(carList);
    }

    public Car findCarById(String carId) {
        for (Car car : carList) {
            if (car.getCarId().equalsIgnoreCase(carId)) {
                return car;
            }
        }
        return null;
    }

    public boolean addCar(String brand, String model, int year, String type, double pricePerDay) {
        String carId = generateCarId();
        Car car = new Car(carId, brand, model, year, type, pricePerDay, "AVAILABLE");

        carList.add(car);
        saveCars();

        return true;
    }

    public boolean addCar(Car car) {
        if (car == null) {
            return false;
        }

        if (car.getCarId() == null || car.getCarId().trim().isEmpty()) {
            car.setCarId(generateCarId());
        }

        if (findCarById(car.getCarId()) != null) {
            return false;
        }

        carList.add(car);
        saveCars();

        return true;
    }

    public boolean editCar(String carId, String brand, String model, int year, String type, double pricePerDay) {
        Car car = findCarById(carId);

        if (car == null) {
            return false;
        }

        car.updateCar(brand, model, year, type, pricePerDay);

        for (Rental rental : rentalList) {
            if (rental.getCar().getCarId().equalsIgnoreCase(carId)) {
                rental.calculatePrice();
            }
        }

        saveCars();
        saveRentals();

        return true;
    }

    public boolean deleteCar(String carId, User currentUser) {
        if (currentUser == null || !currentUser.canDeleteCar()) {
            return false;
        }

        Car car = findCarById(carId);

        if (car == null) {
            return false;
        }

        if ("RENTED".equalsIgnoreCase(car.getStatus())) {
            return false;
        }

        carList.remove(car);
        saveCars();

        return true;
    }

    public ArrayList<Car> searchCars(String query) {
        ArrayList<Car> results = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return getAllCars();
        }

        String lowerQuery = query.toLowerCase().trim();

        for (Car car : carList) {
            if (car.getBrand().toLowerCase().contains(lowerQuery)
                    || car.getModel().toLowerCase().contains(lowerQuery)) {
                results.add(car);
            }
        }

        return results;
    }

    public ArrayList<Rental> getAllRentals() {
        return new ArrayList<>(rentalList);
    }

    public Rental findRentalById(String rentalId) {
        for (Rental rental : rentalList) {
            if (rental.getRentalId().equalsIgnoreCase(rentalId)) {
                return rental;
            }
        }
        return null;
    }

    public Rental rentCar(String carId, String customerName, String customerPhone, int days, String rentalStartDate) {
        Car car = findCarById(carId);

        if (car == null || !car.isAvailable() || days <= 0) {
            return null;
        }

        // Calculate expectedReturnDate
        String expectedReturnDate = "-";
        try {
            java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
            java.time.LocalDate start = java.time.LocalDate.parse(rentalStartDate, dtf);
            expectedReturnDate = start.plusDays(days).format(dtf);
        } catch (Exception e) {
            // Fallback
        }

        String customerId = generateCustomerId();
        Customer customer = new Customer(customerId, customerName, customerPhone);
        customerList.add(customer);

        String rentalId = generateRentalId();
        Rental rental = new Rental(rentalId, car, customer, days, rentalStartDate, expectedReturnDate);
        rental.confirmRental();
        rentalList.add(rental);

        saveCustomers();
        saveRentals();
        saveCars();

        return rental;
    }

    public Rental rentCar(String carId, String customerName, String customerPhone, int days) {
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return rentCar(carId, customerName, customerPhone, days, java.time.LocalDate.now().format(dtf));
    }

    public boolean returnCar(String rentalId) {
        Rental rental = findRentalById(rentalId);

        if (rental == null) {
            return false;
        }

        if ("RETURNED".equalsIgnoreCase(rental.getStatus())) {
            return false;
        }

        rental.returnCar();

        saveRentals();
        saveCars();

        return true;
    }

    public int getTotalCars() {
        return carList.size();
    }

    public int getAvailableCarCount() {
        int count = 0;

        for (Car car : carList) {
            if (car.isAvailable()) {
                count++;
            }
        }

        return count;
    }

    public int getRentedCarCount() {
        int count = 0;

        for (Car car : carList) {
            if ("RENTED".equalsIgnoreCase(car.getStatus())) {
                count++;
            }
        }

        return count;
    }

    public int getTotalRentalCount() {
        return rentalList.size();
    }

    private String generateCarId() {
        int max = 0;

        for (Car car : carList) {
            String id = car.getCarId();

            if (id != null && id.startsWith("CAR")) {
                try {
                    int number = Integer.parseInt(id.substring(3));

                    if (number > max) {
                        max = number;
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid ID format.
                }
            }
        }

        return String.format("CAR%03d", max + 1);
    }

    private String generateCustomerId() {
        int max = 0;

        for (Customer customer : customerList) {
            String id = customer.getCustomerId();

            if (id != null && id.startsWith("CUST")) {
                try {
                    int number = Integer.parseInt(id.substring(4));

                    if (number > max) {
                        max = number;
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid ID format.
                }
            }
        }

        return String.format("CUST%03d", max + 1);
    }

    private String generateRentalId() {
        int max = 0;

        for (Rental rental : rentalList) {
            String id = rental.getRentalId();

            if (id != null && id.startsWith("REN")) {
                try {
                    int number = Integer.parseInt(id.substring(3));

                    if (number > max) {
                        max = number;
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid ID format.
                }
            }
        }

        return String.format("REN%03d", max + 1);
    }

    public void loadData() {
        loadUsers();
        loadCars();
        loadCustomers();
        loadRentals();
    }

    public void saveData() {
        saveUsers();
        saveCars();
        saveCustomers();
        saveRentals();
    }

    private void createDataFilesIfMissing() {
        new File(DATA_FOLDER).mkdirs();
        new File(REPORT_FOLDER).mkdirs();

        createFileIfMissing(DATA_FOLDER + "/users.txt");
        createFileIfMissing(DATA_FOLDER + "/cars.txt");
        createFileIfMissing(DATA_FOLDER + "/customers.txt");
        createFileIfMissing(DATA_FOLDER + "/rentals.txt");
    }

    private void createFileIfMissing(String path) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + path);
        }
    }

    private void loadUsers() {
        userList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FOLDER + "/users.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    String status = "ACTIVE"; // Default status for legacy formats

                    if (parts.length == 4) {
                        status = parts[3];
                    }

                    if ("Admin".equalsIgnoreCase(role)) {
                        userList.add(new Admin(username, password, status));
                    } else if ("Staff".equalsIgnoreCase(role)) {
                        userList.add(new Staff(username, password, status));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FOLDER + "/users.txt"))) {
            for (User user : userList) {
                writer.println(user.toDataString());
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private void loadCars() {
        carList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FOLDER + "/cars.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 7) {
                    try {
                        String carId = parts[0];
                        String brand = parts[1];
                        String model = parts[2];
                        int year = Integer.parseInt(parts[3]);
                        String type = parts[4];
                        double pricePerDay = Double.parseDouble(parts[5]);
                        String status = parts[6];

                        Car car = new Car(carId, brand, model, year, type, pricePerDay, status);
                        carList.add(car);
                    } catch (NumberFormatException e) {
                        // Ignore invalid car record.
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading cars: " + e.getMessage());
        }
    }

    private void saveCars() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FOLDER + "/cars.txt"))) {
            for (Car car : carList) {
                writer.println(car.toDataString());
            }
        } catch (IOException e) {
            System.out.println("Error saving cars: " + e.getMessage());
        }
    }

    private void loadCustomers() {
        customerList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FOLDER + "/customers.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 3) {
                    Customer customer = new Customer(parts[0], parts[1], parts[2]);
                    customerList.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    private void saveCustomers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FOLDER + "/customers.txt"))) {
            for (Customer customer : customerList) {
                writer.println(customer.toDataString());
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    private void loadRentals() {
        rentalList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FOLDER + "/rentals.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 6 || parts.length == 8) {
                    try {
                        String rentalId = parts[0];
                        String carId = parts[1];
                        String customerId = parts[2];
                        int days = Integer.parseInt(parts[3]);
                        String status = parts[5];
                        String rentalStartDate = (parts.length == 8) ? parts[6] : "-";
                        String expectedReturnDate = (parts.length == 8) ? parts[7] : "-";

                        Car car = findCarById(carId);
                        Customer customer = findCustomerById(customerId);

                        if (car != null && customer != null) {
                            Rental rental = new Rental(rentalId, car, customer, days, rentalStartDate, expectedReturnDate);
                            rental.setStatus(status);
                            rental.calculatePrice();
                            rentalList.add(rental);
                        }
                    } catch (NumberFormatException e) {
                        // Ignore invalid rental record.
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading rentals: " + e.getMessage());
        }
    }

    private void saveRentals() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FOLDER + "/rentals.txt"))) {
            for (Rental rental : rentalList) {
                writer.println(rental.toDataString());
            }
        } catch (IOException e) {
            System.out.println("Error saving rentals: " + e.getMessage());
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<>(customerList);
    }

    public ArrayList<Customer> searchCustomers(String query) {
        ArrayList<Customer> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return getAllCustomers();
        }
        String lowerQuery = query.toLowerCase().trim();
        for (Customer customer : customerList) {
            if (customer.getName().toLowerCase().contains(lowerQuery)
                    || customer.getPhone().toLowerCase().contains(lowerQuery)
                    || customer.getCustomerId().toLowerCase().contains(lowerQuery)) {
                results.add(customer);
            }
        }
        return results;
    }

    private Customer findCustomerById(String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public boolean exportInventoryReport() {
        new File(REPORT_FOLDER).mkdirs();

        int activeRentals = 0;
        int returnedRentals = 0;
        for (Rental rental : rentalList) {
            if ("ACTIVE".equalsIgnoreCase(rental.getStatus())) {
                activeRentals++;
            } else if ("RETURNED".equalsIgnoreCase(rental.getStatus())) {
                returnedRentals++;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FOLDER + "/driveflow_report.txt"))) {
            writer.write("====================================================================================================================================\n");
            writer.write("                                                   DRIVEFLOW MANAGEMENT REPORT                                                      \n");
            writer.write("====================================================================================================================================\n");
            writer.write("Generated on: " + new Date() + "\n\n");

            writer.write("SUMMARY INFORMATION\n");
            writer.write("------------------------------------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format("%-25s: %-10d\n", "Total Cars", carList.size()));
            writer.write(String.format("%-25s: %-10d\n", "Available Cars", getAvailableCarCount()));
            writer.write(String.format("%-25s: %-10d\n", "Rented Cars", getRentedCarCount()));
            writer.write(String.format("%-25s: %-10d\n", "Total Rentals", rentalList.size()));
            writer.write(String.format("%-25s: %-10d\n", "Active Rentals", activeRentals));
            writer.write(String.format("%-25s: %-10d\n", "Returned Rentals", returnedRentals));
            writer.write("\n");

            writer.write("CAR INVENTORY SECTION\n");
            writer.write("------------------------------------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format(
                    "%-10s | %-15s | %-15s | %-6s | %-12s | %-12s | %-12s\n",
                    "CAR ID", "BRAND", "MODEL", "YEAR", "TYPE", "PRICE/DAY", "STATUS"));
            writer.write("------------------------------------------------------------------------------------------------------------------------------------\n");

            for (Car car : carList) {
                writer.write(String.format(
                        "%-10s | %-15s | %-15s | %-6d | %-12s | RM%10.2f | %-12s\n",
                        car.getCarId(),
                        car.getBrand(),
                        car.getModel(),
                        car.getYear(),
                        car.getType(),
                        car.getPricePerDay(),
                        car.getStatus()));
            }
            writer.write("\n");

            writer.write("RENTAL RECORDS SECTION\n");
            writer.write("------------------------------------------------------------------------------------------------------------------------------------\n");
            writer.write(String.format(
                    "%-10s | %-10s | %-20s | %-12s | %-12s | %-15s | %-4s | %-12s | %-10s\n",
                    "RENTAL ID", "CAR ID", "CUSTOMER", "PHONE", "START DATE", "EXPECTED RETURN", "DAYS", "TOTAL PRICE", "STATUS"));
            writer.write("------------------------------------------------------------------------------------------------------------------------------------\n");

            for (int i = rentalList.size() - 1; i >= 0; i--) {
                Rental rental = rentalList.get(i);
                String startDate = (rental.getRentalStartDate() == null || rental.getRentalStartDate().trim().isEmpty()) ? "-" : rental.getRentalStartDate();
                String returnDate = (rental.getExpectedReturnDate() == null || rental.getExpectedReturnDate().trim().isEmpty()) ? "-" : rental.getExpectedReturnDate();
                
                writer.write(String.format(
                        "%-10s | %-10s | %-20s | %-12s | %-12s | %-15s | %-4d | RM%10.2f | %-10s\n",
                        rental.getRentalId(),
                        rental.getCar().getCarId(),
                        rental.getCustomer().getName(),
                        rental.getCustomer().getPhone(),
                        startDate,
                        returnDate,
                        rental.getDays(),
                        rental.getTotalPrice(),
                        rental.getStatus()));
            }

            writer.write("====================================================================================================================================\n");

            return true;
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
            return false;
        }
    }
}