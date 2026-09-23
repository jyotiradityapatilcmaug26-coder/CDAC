import java.io.*;
import java.util.*;

// ================= EMPLOYEE =================

abstract class Employee implements Serializable {

    protected String name;
    protected String address;
    protected int age;
    protected String gender;
    protected double basicSalary;

    public Employee(String name, String address, int age,
                    String gender, double basicSalary) {

        this.name = name;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.basicSalary = basicSalary;
    }

    public abstract void display();

    public abstract String getType();
}


// ================= MANAGER =================

class Manager extends Employee {

    private double hra;

    public Manager(String name, String address, int age,
                   String gender, double basicSalary, double hra) {

        super(name, address, age, gender, basicSalary);
        this.hra = hra;
    }

    @Override
    public void display() {

        System.out.println("\n----- Manager -----");
        System.out.println("Name         : " + name);
        System.out.println("Address      : " + address);
        System.out.println("Age          : " + age);
        System.out.println("Gender       : " + gender);
        System.out.println("Basic Salary : " + basicSalary);
        System.out.println("HRA          : " + hra);
    }

    @Override
    public String getType() {
        return "Manager";
    }
}


// ================= ENGINEER =================

class Engineer extends Employee {

    private String technology;

    public Engineer(String name, String address, int age,
                    String gender, double basicSalary,
                    String technology) {

        super(name, address, age, gender, basicSalary);
        this.technology = technology;
    }

    @Override
    public void display() {

        System.out.println("\n----- Engineer -----");
        System.out.println("Name         : " + name);
        System.out.println("Address      : " + address);
        System.out.println("Age          : " + age);
        System.out.println("Gender       : " + gender);
        System.out.println("Basic Salary : " + basicSalary);
        System.out.println("Technology   : " + technology);
    }

    @Override
    public String getType() {
        return "Engineer";
    }
}


// ================= SALES PERSON =================

class SalesPerson extends Employee {

    private double salesTarget;

    public SalesPerson(String name, String address, int age,
                       String gender, double basicSalary,
                       double salesTarget) {

        super(name, address, age, gender, basicSalary);
        this.salesTarget = salesTarget;
    }

    @Override
    public void display() {

        System.out.println("\n----- Sales Person -----");
        System.out.println("Name         : " + name);
        System.out.println("Address      : " + address);
        System.out.println("Age          : " + age);
        System.out.println("Gender       : " + gender);
        System.out.println("Basic Salary : " + basicSalary);
        System.out.println("Sales Target : " + salesTarget);
    }

    @Override
    public String getType() {
        return "SalesPerson";
    }
}


// ================= NODE =================

class Node {

    Employee employee;
    Node prev;
    Node next;

    public Node(Employee employee) {
        this.employee = employee;
        this.prev = null;
        this.next = null;
    }
}


// ================= EMPLOYEE LIST =================

class EmployeeList {

    private Node head;
    private Node tail;

    // ---------- ADD ----------

    public void addEmployee(Employee employee) {

        Node newNode = new Node(employee);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        System.out.println("Employee added successfully.");
    }


    // ---------- DISPLAY ----------

    public void displayEmployees() {

        if (head == null) {
            System.out.println("No employees available.");
            return;
        }

        Node temp = head;

        while (temp != null) {
            temp.employee.display();
            temp = temp.next;
        }
    }


    // ---------- DELETE ----------

    public void deleteEmployee(String name) {

        Node temp = head;

        while (temp != null) {

            if (temp.employee.name.equalsIgnoreCase(name)) {

                if (temp.prev != null) {
                    temp.prev.next = temp.next;
                } else {
                    head = temp.next;
                }

                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                } else {
                    tail = temp.prev;
                }

                System.out.println("Employee deleted successfully.");
                return;
            }

            temp = temp.next;
        }

        System.out.println("Employee not found.");
    }


    // ---------- SORT ASCENDING ----------

    public void sortAscending() {

        if (head == null || head.next == null)
            return;

        boolean swapped;

        do {

            swapped = false;
            Node temp = head;

            while (temp.next != null) {

                if (temp.employee.name.compareToIgnoreCase(
                        temp.next.employee.name) > 0) {

                    Employee e = temp.employee;
                    temp.employee = temp.next.employee;
                    temp.next.employee = e;

                    swapped = true;
                }

                temp = temp.next;
            }

        } while (swapped);

        System.out.println("Employees sorted in ascending order.");
    }


    // ---------- SORT DESCENDING ----------

    public void sortDescending() {

        if (head == null || head.next == null)
            return;

        boolean swapped;

        do {

            swapped = false;
            Node temp = head;

            while (temp.next != null) {

                if (temp.employee.name.compareToIgnoreCase(
                        temp.next.employee.name) < 0) {

                    Employee e = temp.employee;
                    temp.employee = temp.next.employee;
                    temp.next.employee = e;

                    swapped = true;
                }

                temp = temp.next;
            }

        } while (swapped);

        System.out.println("Employees sorted in descending order.");
    }


    // ---------- SAVE ----------

    public void saveToFile(String fileName) {

        try {

            ArrayList<Employee> list = new ArrayList<>();

            Node temp = head;

            while (temp != null) {
                list.add(temp.employee);
                temp = temp.next;
            }

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(fileName));

            out.writeObject(list);
            out.close();

            System.out.println("Employees saved successfully.");

        } catch (IOException e) {

            System.out.println("Error while saving file.");
        }
    }


    // ---------- LOAD ----------

    @SuppressWarnings("unchecked")
    public void loadFromFile(String fileName) {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream(fileName));

            ArrayList<Employee> list =
                    (ArrayList<Employee>) in.readObject();

            in.close();

            head = null;
            tail = null;

            for (Employee employee : list) {
                addEmployee(employee);
            }

            System.out.println("Employees loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error while loading file.");
        }
    }
}


// ================= MAIN =================

public class Day6_Employee {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeList employeeList = new EmployeeList();

        int choice;

        do {

            System.out.println("\n========== EMPLOYEE MANAGEMENT ==========");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Delete an Employee");
            System.out.println("4. Sort Employees");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\nSelect Employee Type:");
                    System.out.println("1. Manager");
                    System.out.println("2. Engineer");
                    System.out.println("3. Sales Person");
                    System.out.print("Enter type: ");

                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Gender: ");
                    String gender = sc.nextLine();

                    System.out.print("Enter Basic Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine();

                    if (type == 1) {

                        System.out.print("Enter HRA: ");
                        double hra = sc.nextDouble();
                        sc.nextLine();

                        employeeList.addEmployee(
                                new Manager(
                                        name, address, age,
                                        gender, salary, hra));

                    } else if (type == 2) {

                        System.out.print("Enter Technology: ");
                        String technology = sc.nextLine();

                        employeeList.addEmployee(
                                new Engineer(
                                        name, address, age,
                                        gender, salary, technology));

                    } else if (type == 3) {

                        System.out.print("Enter Sales Target: ");
                        double target = sc.nextDouble();
                        sc.nextLine();

                        employeeList.addEmployee(
                                new SalesPerson(
                                        name, address, age,
                                        gender, salary, target));

                    } else {

                        System.out.println("Invalid employee type.");
                    }

                    break;


                case 2:

                    employeeList.displayEmployees();

                    break;


                case 3:

                    System.out.print("Enter employee name to delete: ");
                    String deleteName = sc.nextLine();

                    employeeList.deleteEmployee(deleteName);

                    break;


                case 4:

                    System.out.println("\n1. Ascending");
                    System.out.println("2. Descending");
                    System.out.print("Enter choice: ");

                    int sortChoice = sc.nextInt();
                    sc.nextLine();

                    if (sortChoice == 1) {

                        employeeList.sortAscending();

                    } else if (sortChoice == 2) {

                        employeeList.sortDescending();

                    } else {

                        System.out.println("Invalid choice.");
                    }

                    break;


                case 5:

                    employeeList.saveToFile("employees.dat");

                    break;


                case 6:

                    employeeList.loadFromFile("employees.dat");

                    break;


                case 7:

                    System.out.println("Exiting program...");

                    break;


                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        sc.close();
    }
}