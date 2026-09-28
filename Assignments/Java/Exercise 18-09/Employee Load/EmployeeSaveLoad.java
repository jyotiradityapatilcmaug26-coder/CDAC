
import java.io.*;
import java.util.*;

// Employee class
class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
        System.out.println("--------------------");
    }
}

// Main class
public class EmployeeSaveLoad {

    static ArrayList<Employee> employees = new ArrayList<>();

    static final String FILE_NAME = "employees.dat";

    // Save all Employee objects to a file
    public static void saveEmployees() {

        try {
            ObjectOutputStream out =
                new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME));

            out.writeObject(employees);
            out.close();

            System.out.println("Employees saved successfully.");

        } catch (IOException e) {
            System.out.println("Error while saving employees.");
        }
    }

    // Load all Employee objects from a file
    @SuppressWarnings("unchecked")
    public static void loadEmployees() {

        try {
            ObjectInputStream in =
                new ObjectInputStream(
                    new FileInputStream(FILE_NAME));

            employees =
                (ArrayList<Employee>) in.readObject();

            in.close();

            System.out.println("Employees loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading employees.");
        }
    }

    // Display Employee objects
    public static void displayEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }

        for (Employee employee : employees) {
            employee.display();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n===== EMPLOYEE MENU =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Save Employees");
            System.out.println("4. Load Employees");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Employee Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();

                    employees.add(
                        new Employee(id, name, salary));

                    System.out.println("Employee added successfully.");
                    break;

                case 2:
                    displayEmployees();
                    break;

                case 3:
                    saveEmployees();
                    break;

                case 4:
                    loadEmployees();
                    break;

                case 5:
                    System.out.println("Program terminated.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        sc.close();
    }
}