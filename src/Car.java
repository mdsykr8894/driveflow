public class Car {
    private String carId;
    private String brand;
    private String model;
    private int year;
    private String type;
    private double pricePerDay;
    private String status;

    public Car(String carId, String brand, String model, int year, String type, double pricePerDay, String status) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateCar(String brand, String model, int year, String type, double pricePerDay) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.type = type;
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return "AVAILABLE".equalsIgnoreCase(status);
    }

    public String getDetails() {
        return carId + " - " + brand + " " + model + " (" + year + ") - " + status;
    }

    public String toDataString() {
        return carId + "|" + brand + "|" + model + "|" + year + "|" + type + "|" + pricePerDay + "|" + status;
    }
}
