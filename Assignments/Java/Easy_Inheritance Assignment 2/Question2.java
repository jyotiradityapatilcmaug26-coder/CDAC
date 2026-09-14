import java.util.Scanner;

// Base Class: Member
class Member {
    // Data members
    private String name;
    private int age;
    private String phoneNumber;
    private String address;
    private double salary;

    // Default Constructor
    public Member() {}

    // Parameterized Constructor
    public Member(String name, int age, String phoneNumber, String address, double salary) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.salary = salary;
    }

    // Accessors (Getters) and Mutators (Setters)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Method to print salary
    public void printSalary() {
        System.out.println("Salary: " + salary);
    }

    // Method to display basic member details
    public void displayMember() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
        printSalary();
    }
}

// Derived Class: PrimeMembers
class PrimeMembers extends Member {
    // Additional properties
    private int joiningYear;
    private double joiningFees;
    private boolean isActive;

    // Default Constructor
    public PrimeMembers() {}

    // Parameterized Constructor
    public PrimeMembers(String name, int age, String phoneNumber, String address, double salary,
                        int joiningYear, double joiningFees, boolean isActive) {
        super(name, age, phoneNumber, address, salary);
        this.joiningYear = joiningYear;
        this.joiningFees = joiningFees;
        this.isActive = isActive;
    }

    // Accessors (Getters) and Mutators (Setters)
    public int getJoiningYear() {
        return joiningYear;
    }

    public void setJoiningYear(int joiningYear) {
        this.joiningYear = joiningYear;
    }

    public double getJoiningFees() {
        return joiningFees;
    }

    public void setJoiningFees(double joiningFees) {
        this.joiningFees = joiningFees;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Method to display all fields from both Parent and Derived class
    public void display() {
        System.out.println("\n--- Prime Member Details ---");
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Address: " + getAddress());
        printSalary();
        System.out.println("Joining Year: " + joiningYear);
        System.out.println("Joining Fees: " + joiningFees);
        System.out.println("Active Status: " + (isActive ? "Active" : "Inactive"));
    }
}

// Main class to test the implementation with console input
public class Question2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Step 1: Input for regular Member ---
        System.out.println("=== Enter details for regular Member ===");
        Member member = new Member();

        System.out.print("Enter Name: ");
        member.setName(scanner.nextLine());

        System.out.print("Enter Age: ");
        member.setAge(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Phone Number: ");
        member.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Address: ");
        member.setAddress(scanner.nextLine());

        System.out.print("Enter Salary: ");
        member.setSalary(Double.parseDouble(scanner.nextLine()));

        System.out.println("\n--- Regular Member Information ---");
        member.displayMember();

        // --- Step 2: Input for PrimeMembers ---
        System.out.println("\n=== Enter details for Prime Member ===");
        PrimeMembers primeMember = new PrimeMembers();

        System.out.print("Enter Name: ");
        primeMember.setName(scanner.nextLine());

        System.out.print("Enter Age: ");
        primeMember.setAge(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Phone Number: ");
        primeMember.setPhoneNumber(scanner.nextLine());

        System.out.print("Enter Address: ");
        primeMember.setAddress(scanner.nextLine());

        System.out.print("Enter Salary: ");
        primeMember.setSalary(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter Joining Year: ");
        primeMember.setJoiningYear(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter Joining Fees: ");
        primeMember.setJoiningFees(Double.parseDouble(scanner.nextLine()));

        System.out.print("Is Active (true/false): ");
        primeMember.setActive(Boolean.parseBoolean(scanner.nextLine()));

        // Display all details using display() method
        primeMember.display();

        scanner.close();
    }
}