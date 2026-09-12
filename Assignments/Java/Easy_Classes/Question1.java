// Definition of the Student class
class Student {
    String name;
    int roll_no;
    String phone_no;
    String address;
}

public class Question1 {
    public static void main(String[] args) {
        
        // Part (a): Create an object and assign name "John" and roll_no 2

        Student studentA = new Student();
        studentA.name = "John";
        studentA.roll_no = 2;

        System.out.println("=== Part (a) ===");
        System.out.println("Name: " + studentA.name);
        System.out.println("Roll No: " + studentA.roll_no);
        System.out.println();

        // Part (b): Create two objects for 'Sam' and 'John',
        //           assign details, and print them.
        
        Student sam = new Student();
        sam.name = "Sam";
        sam.roll_no = 1;
        sam.phone_no = "9876543210";
        sam.address = "123 Baker Street, London";

        Student john = new Student();
        john.name = "John";
        john.roll_no = 2;
        john.phone_no = "9123456780";
        john.address = "456 Elm Street, New York";

        System.out.println("=== Part (b) ===");
        System.out.println("Student 1:");
        System.out.println("Name        : " + sam.name);
        System.out.println("Roll Number : " + sam.roll_no);
        System.out.println("Phone Number: " + sam.phone_no);
        System.out.println("Address     : " + sam.address);

        System.out.println();

        System.out.println("Student 2:");
        System.out.println("Name        : " + john.name);
        System.out.println("Roll Number : " + john.roll_no);
        System.out.println("Phone Number: " + john.phone_no);
        System.out.println("Address     : " + john.address);
    }
}

