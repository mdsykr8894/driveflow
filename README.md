# DriveFlow - Car Rental Management System

DriveFlow is a Java Swing desktop application developed to manage basic car rental operations. The system provides a graphical user interface for handling cars, rental records, users, dashboard summaries, and inventory reports.

## Purpose

The purpose of this project is to demonstrate the implementation of Object-Oriented Programming (OOP) concepts in a car rental management system. This project applies class design, encapsulation, inheritance, polymorphism, object relationships, file handling, GUI development, and basic CRUD operations using Java.

This system is designed as a learning project to show how different classes work together to manage car rental activities such as adding cars, renting cars, returning cars, managing users, and exporting reports.

## Features

- User login system
- Admin and staff roles
- Dashboard summary
- Car management
  - Add new car
  - Edit car details
  - Delete car
  - Search car
  - Display car list

- Rental management
  - Rent available cars
  - Return rented cars
  - View rental records

- User management
  - Create staff account

- Reports
  - View inventory summary
  - Export car inventory report

- File-based data storage using text files

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- File Handling
- Git and GitHub

## Project Structure

```text
DriveFlow/
├── src/
│   ├── Main.java
│   ├── CarRentalApp.java
│   ├── CarRentalSystem.java
│   ├── User.java
│   ├── Admin.java
│   ├── Staff.java
│   ├── Customer.java
│   ├── Car.java
│   └── Rental.java
├── data/
│   ├── users.txt
│   ├── cars.txt
│   ├── customers.txt
│   └── rentals.txt
├── reports/
│   └── car_inventory_report.txt
├── README.md
└── .gitignore
```

## Main Classes

### Main

The entry point of the application. It starts the system and launches the graphical user interface.

### CarRentalApp

The Java Swing GUI class. It manages the login screen, dashboard, car management screen, rental management screen, user management screen, and reports screen.

### CarRentalSystem

The main system logic class. It manages users, cars, customers, rentals, login validation, car operations, rental operations, data loading, data saving, and report export.

### User

The parent class for system users. It stores common user information such as username, password, and role.

### Admin

A subclass of User. Admin users have higher permissions, such as managing users and deleting cars.

### Staff

A subclass of User. Staff users can manage rental operations but have limited permissions compared to Admin.

### Car

Represents a car in the rental system. It stores car details such as car ID, brand, model, year, type, price per day, and status.

### Customer

Represents a customer who rents a car.

### Rental

Represents a rental record. It connects a car with a customer and stores rental details such as rental ID, rental days, total price, and rental status.

## OOP Concepts Applied

### Encapsulation

Class attributes are declared as private and accessed through getter and setter methods.

### Inheritance

Admin and Staff inherit from the User class.

### Polymorphism

Different user roles provide different behavior through methods such as `canManageUsers()` and `canDeleteCar()`.

### Method Overriding

Admin and Staff override methods from the User class to define different permission levels.

### Association

The Rental class is associated with Car and Customer because each rental record is linked to one car and one customer.

### Aggregation

CarRentalSystem manages collections of Car, Rental, Customer, and User objects.

### Abstraction

The GUI interacts with the system through high-level methods such as `addCar()`, `rentCar()`, `returnCar()`, and `exportInventoryReport()` without needing to know the internal implementation details.

## How to Run

1. Open the project folder in an IDE such as VS Code, IntelliJ IDEA, Eclipse, or NetBeans.
2. Make sure Java is installed.
3. Compile the Java files inside the `src` folder.
4. Run `Main.java`.

Example using terminal:

```bash
cd DriveFlow
javac src/*.java
java -cp src Main
```

## Default Login

If no admin account exists, the system creates a default admin account automatically.

```text
Username: admin
Password: admin123
```

## Data Storage

This project uses text files for simple data storage. The data files are stored inside the `data` folder.

```text
data/users.txt
data/cars.txt
data/customers.txt
data/rentals.txt
```

## Report Export

The system can export the car inventory report to:

```text
reports/car_inventory_report.txt
```

## Notes

This project uses text files instead of a database. It is designed for learning purposes and focuses on Java Swing GUI development, file handling, and Object-Oriented Programming concepts.
