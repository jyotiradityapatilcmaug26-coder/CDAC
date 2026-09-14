class Employee {
    String name;
    int yearOfJoining;
    double salary;
    String address;

    // Constructor to initialize all employee details
    Employee(String name, int yearOfJoining, double salary, String address) {
        this.name = name;
        this.yearOfJoining = yearOfJoining;
        this.salary = salary;
        this.address = address;
    }

    // Method to display employee information in tabular format
    void display() {
        System.out.printf("%-12s %-20d %s\n", name, yearOfJoining, address);
    }
}

public class Question5 {
    public static void main(String[] args) {
        // Creating 3 Employee objects with their respective details
        Employee emp1 = new Employee("Robert", 1994, 50000, "64C- WallsStreat");
        Employee emp2 = new Employee("Sam", 2000, 55000, "68D- WallsStreat");
        Employee emp3 = new Employee("John", 1999, 60000, "26B- WallsStreat");

        // Printing table header
        System.out.printf("%-12s %-20s %s\n", "Name", "Year of joining", "Address");

        // Printing each employee's details
        emp1.display();
        emp2.display();
        emp3.display();
    }
}