public class Rental {
    private String rentalId;
    private Car car;
    private Customer customer;
    private int days;
    private double totalPrice;
    private String status;
    private String rentalStartDate;
    private String expectedReturnDate;

    public Rental(String rentalId, Car car, Customer customer, int days, String rentalStartDate, String expectedReturnDate) {
        this.rentalId = rentalId;
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.rentalStartDate = rentalStartDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = "ACTIVE";
        calculatePrice();
    }

    public Rental(String rentalId, Car car, Customer customer, int days) {
        this(rentalId, car, customer, days, "-", "-");
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
        calculatePrice();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
        calculatePrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void calculatePrice() {
        if (car != null) {
            totalPrice = car.getPricePerDay() * days;
        }
    }

    public void confirmRental() {
        status = "ACTIVE";
        if (car != null) {
            car.setStatus("RENTED");
        }
        calculatePrice();
    }

    public void returnCar() {
        status = "RETURNED";
        if (car != null) {
            car.setStatus("AVAILABLE");
        }
    }

    public String getRentalStartDate() {
        return rentalStartDate;
    }

    public String getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public String getRentalDetails() {
        return rentalId + " - " + car.getBrand() + " " + car.getModel() + " rented by " + customer.getName();
    }

    public String toDataString() {
        return rentalId + "|" + car.getCarId() + "|" + customer.getCustomerId() + "|" + days + "|" + totalPrice + "|"
                + status + "|" + rentalStartDate + "|" + expectedReturnDate;
    }
}
