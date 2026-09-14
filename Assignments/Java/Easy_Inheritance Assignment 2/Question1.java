// Parent class
class Parent {
    public void printParentMessage() {
        System.out.println("This is parent class");
    }
}

// Subclass inheriting from Parent class
class Child extends Parent {
    public void printChildMessage() {
        System.out.println("This is child class");
    }
}

// Main class to run the program
public class Question1 {
    public static void main(String[] args) {
        // Create an object of Parent class
        Parent parentObj = new Parent();

        // Create an object of Child class
        Child childObj = new Child();

        System.out.println("--- Output ---");

        // 1 - Method of parent class by object of parent class
        System.out.print("1: ");
        parentObj.printParentMessage();

        // 2 - Method of child class by object of child class
        System.out.print("2: ");
        childObj.printChildMessage();

        // 3 - Method of parent class by object of child class
        System.out.print("3: ");
        childObj.printParentMessage();
    }
}