package Assignments.Java.Exercises;

import java.io.*;
import java.util.*;

// ================= EMPLOYEE =================

abstract class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int id;
    protected String name;
    protected double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "ID: " + id
                + ", Name: " + name
                + ", Salary: " + salary
                + ", Type: " + getType();
    }
}


// ================= MANAGER =================

class Manager extends Employee {

    private static final long serialVersionUID = 1L;

    private String department;

    public Manager(int id, String name, double salary, String department) {
        super(id, name, salary);
        this.department = department;
    }

    @Override
    public String getType() {
        return "Manager";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Department: " + department;
    }
}


// ================= ENGINEER =================

class Engineer extends Employee {

    private static final long serialVersionUID = 1L;

    private String technology;

    public Engineer(int id, String name, double salary, String technology) {
        super(id, name, salary);
        this.technology = technology;
    }

    @Override
    public String getType() {
        return "Engineer";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Technology: " + technology;
    }
}


// ================= SALES PERSON =================

class SalesPerson extends Employee {

    private static final long serialVersionUID = 1L;

    private double salesTarget;

    public SalesPerson(int id, String name, double salary, double salesTarget) {
        super(id, name, salary);
        this.salesTarget = salesTarget;
    }

    @Override
    public String getType() {
        return "Sales Person";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Sales Target: " + salesTarget;
    }
}


// ================= NODE =================

class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    Employee employee;
    Node next;
    Node previous;

    public Node(Employee employee) {
        this.employee = employee;
    }
}


// ================= DOUBLY LINKED LIST =================

class EmployeeList implements Serializable {

    private static final long serialVersionUID = 1L;

    private Node head;
    private Node tail;

    private transient Node current;

    // Add employee
    public void add(Employee employee) {

        Node newNode = new Node(employee);

        if (head == null) {

            head = newNode;
            tail = newNode;
            current = head;

        } else {

            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }

        System.out.println("Employee added successfully.");
    }


    // Display all employees
    public void displayAll() {

        if (head == null) {
            System.out.println("No employees available.");
            return;
        }

        Node temp = head;

        while (temp != null) {

            System.out.println(temp.employee);

            temp = temp.next;
        }
    }


    // First employee
    public void firstEmployee() {

        if (head == null) {
            System.out.println("No employees available.");
            return;
        }

        current = head;

        System.out.println("First Employee:");
        System.out.println(current.employee);
    }


    // Next employee
    public void nextEmployee() {

        if (head == null) {
            System.out.println("No employees available.");
            return;
        }

        if (current == null) {

            current = head;

        } else if (current.next != null) {

            current = current.next;

        } else {

            System.out.println("Already at the last employee.");
            return;
        }

        System.out.println("Next Employee:");
        System.out.println(current.employee);
    }


    // Previous employee
    public void previousEmployee() {

        if (head == null) {
            System.out.println("No employees available.");
            return;
        }

        if (current == null) {

            current = tail;

        } else if (current.previous != null) {

            current = current.previous;

        } else {

            System.out.println("Already at the first employee.");
            return;
        }

        System.out.println("Previous Employee:");
        System.out.println(current.employee);
    }


    // Last employee
    public void lastEmployee() {

        if (tail == null) {
            System.out.println("No employees available.");
            return;
        }

        current = tail;

        System.out.println("Last Employee:");
        System.out.println(current.employee);
    }


    // Display all Managers
    public void displayManagers() {

        Node temp = head;
        boolean found = false;

        while (temp != null) {

            if (temp.employee instanceof Manager) {

                System.out.println(temp.employee);
                found = true;
            }

            temp = temp.next;
        }

        if (!found) {
            System.out.println("No Managers found.");
        }
    }


    // Display all Engineers
    public void displayEngineers() {

        Node temp = head;
        boolean found = false;

        while (temp != null) {

            if (temp.employee instanceof Engineer) {

                System.out.println(temp.employee);
                found = true;
            }

            temp = temp.next;
        }

        if (!found) {
            System.out.println("No Engineers found.");
        }
    }


    // Display all Sales Persons
    public void displaySalesPersons() {

        Node temp = head;
        boolean found = false;

        while (temp != null) {

            if (temp.employee instanceof SalesPerson) {

                System.out.println(temp.employee);
                found = true;
            }

            temp = temp.next;
        }

        if (!found) {
            System.out.println("No Sales Persons found.");
        }
    }


    // Sort ascending
    public void sortAscending() {

        if (head == null || head.next == null) {
            return;
        }

        boolean swapped;

        do {

            swapped = false;

            Node temp = head;

            while (temp.next != null) {

                if (temp.employee.getName()
                        .compareToIgnoreCase(temp.next.employee.getName()) > 0) {

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


    // Sort descending
    public void sortDescending() {

        if (head == null || head.next == null) {
            return;
        }

        boolean swapped;

        do {

            swapped = false;

            Node temp = head;

            while (temp.next != null) {

                if (temp.employee.getName()
                        .compareToIgnoreCase(temp.next.employee.getName()) < 0) {

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
}


// ================= MAIN PROGRAM =================

public class Day6_Employee {

    static Scanner sc = new Scanner(System.in);

    static EmployeeList employeeList = new EmployeeList();


    // ================= ADD MENU =================

    public static void addEmployeeMenu() {

        int choice;

        do {

            System.out.println("\n===== ADD EMPLOYEE =====");
            System.out.println("1. Manager");
            System.out.println("2. Engineer");
            System.out.println("3. Sales Person");
            System.out.println("4. Exit to Main Menu");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addManager();
                    break;

                case 2:
                    addEngineer();
                    break;

                case 3:
                    addSalesPerson();
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);
    }


    // ================= ADD MANAGER =================

    public static void addManager() {

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        Manager manager =
                new Manager(id, name, salary, department);

        employeeList.add(manager);
    }


    // ================= ADD ENGINEER =================

    public static void addEngineer() {

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Technology: ");
        String technology = sc.nextLine();

        Engineer engineer =
                new Engineer(id, name, salary, technology);

        employeeList.add(engineer);
    }


    // ================= ADD SALES PERSON =================

    public static void addSalesPerson() {

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        System.out.print("Enter Sales Target: ");
        double salesTarget = sc.nextDouble();
        sc.nextLine();

        SalesPerson salesPerson =
                new SalesPerson(id, name, salary, salesTarget);

        employeeList.add(salesPerson);
    }


    // ================= DISPLAY MENU =================

    public static void displayMenu() {

        int choice;

        do {

            System.out.println("\n===== DISPLAY =====");
            System.out.println("1. All Employees");
            System.out.println("2. First Employee");
            System.out.println("3. Next Employee");
            System.out.println("4. Previous Employee");
            System.out.println("5. Last Employee");
            System.out.println("6. Exit to Main Menu");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    employeeList.displayAll();
                    break;

                case 2:
                    employeeList.firstEmployee();
                    break;

                case 3:
                    employeeList.nextEmployee();
                    break;

                case 4:
                    employeeList.previousEmployee();
                    break;

                case 5:
                    employeeList.lastEmployee();
                    break;

                case 6:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);
    }


    // ================= SORT MENU =================

    public static void sortMenu() {

        int choice;

        do {

            System.out.println("\n===== SORT =====");
            System.out.println("1. All Managers");
            System.out.println("2. All Engineers");
            System.out.println("3. All Sales Person");
            System.out.println("4. All Employees Alphabetic Ascending");
            System.out.println("5. All Employees Alphabetic Descending");
            System.out.println("6. Exit to Main Menu");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    employeeList.displayManagers();
                    break;

                case 2:
                    employeeList.displayEngineers();
                    break;

                case 3:
                    employeeList.displaySalesPersons();
                    break;

                case 4:
                    employeeList.sortAscending();
                    employeeList.displayAll();
                    break;

                case 5:
                    employeeList.sortDescending();
                    employeeList.displayAll();
                    break;

                case 6:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);
    }


    // ================= SAVE TO FILE =================

    public static void saveToFile() {

        try {

            FileOutputStream fos =
                    new FileOutputStream("employees.dat");

            ObjectOutputStream oos =
                    new ObjectOutputStream(fos);

            oos.writeObject(employeeList);

            oos.close();
            fos.close();

            System.out.println("Employees saved successfully.");

        } catch (IOException e) {

            System.out.println("Error while saving file.");
            System.out.println(e.getMessage());
        }
    }


    // ================= LOAD FROM FILE =================

    public static void loadFromFile() {

        try {

            FileInputStream fis =
                    new FileInputStream("employees.dat");

            ObjectInputStream ois =
                    new ObjectInputStream(fis);

            employeeList =
                    (EmployeeList) ois.readObject();

            ois.close();
            fis.close();

            System.out.println("Employees loaded successfully.");

        } catch (FileNotFoundException e) {

            System.out.println("File not found.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error while loading file.");
            System.out.println(e.getMessage());
        }
    }


    // ================= MAIN =================

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("       EMPLOYEE MANAGEMENT");
            System.out.println("=================================");

            System.out.println("1. Add an Employee");
            System.out.println("2. Display");
            System.out.println("3. Sort");
            System.out.println("4. Save to File");
            System.out.println("5. Load from File");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addEmployeeMenu();
                    break;

                case 2:
                    displayMenu();
                    break;

                case 3:
                    sortMenu();
                    break;

                case 4:
                    saveToFile();
                    break;

                case 5:
                    loadFromFile();
                    break;

                case 6:
                    System.out.println("Program terminated.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}