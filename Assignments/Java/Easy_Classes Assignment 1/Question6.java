import java.util.Scanner;

// Definition of the Employee class
class Employee {
    double salary;
    int hoursOfWork;

    // 1 - Takes salary and number of hours of work per day as parameters
    void getInfo(double salary, int hoursOfWork) {
        this.salary = salary;
        this.hoursOfWork = hoursOfWork;
    }

    // 2 - Adds $10 to salary if it is less than $500
    void addSal() {
        if (salary < 500) {
            salary += 10;
        }
    }

    // 3 - Adds $5 to salary if hours of work per day is more than 6 hours
    void addWork() {
        if (hoursOfWork > 6) {
            salary += 5;
        }
    }

    // Method to display the final salary
    void printFinalSalary() {
        System.out.println("Final Salary: $" + salary);
    }
}

// Main class named Question6
public class Question6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee emp = new Employee();

        // Taking input from user
        System.out.print("Enter employee's base salary: $");
        double initialSalary = scanner.nextDouble();

        System.out.print("Enter number of work hours per day: ");
        int hours = scanner.nextInt();

        // Step 1: Set information using getInfo()
        emp.getInfo(initialSalary, hours);

        // Step 2: Check and apply salary bonuses
        emp.addSal();
        emp.addWork();

        // Step 3: Print the final salary
        System.out.println("\n=== Result ===");
        emp.printFinalSalary();

        scanner.close();
    }
}